package com.proj.pampukh.services.implementations;

import com.proj.pampukh.dto.user.UserUpdateDto;
import com.proj.pampukh.persistence.AppUserRepository;
import com.proj.pampukh.persistence.entity.AppUser;
import com.proj.pampukh.persistence.entity.Password;
import com.proj.pampukh.security.JwtUtil;
import com.proj.pampukh.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final AppUserRepository userRepository;

  @Value("${global.pfp}")
  private String pfp_path;

  //TODO: TESTING!!
  public UserServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AppUserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
  }

  private void checkPrincipal(String givenUsername) {
    String principalName =  SecurityContextHolder.getContext().getAuthentication().getName();
    if (!principalName.equals(givenUsername)) throw new RuntimeException("you cant change this user!");
  }

  @Override
  public String update(UserUpdateDto userDto) {
    checkPrincipal(userDto.name());

    Optional<AppUser> found = userRepository.findAppUserByUsername(userDto.name());
    if (found.isEmpty()) throw new RuntimeException("we dont change ghosts here!");

    AppUser updated = found.get();
    var newPass = new Password();
    newPass.setUser(updated);
    newPass.setHash(userDto.password());
    updated.setPassword(newPass);

    userRepository.save(updated);

    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userDto.name(), userDto.password()));

    return jwtUtil.generateAuthToken(auth);
  }

  @Override
  public void delete(String username) {
    checkPrincipal(username);

    Optional<AppUser> found = userRepository.findAppUserByUsername(username);
    if (found.isEmpty()) throw new RuntimeException("cannot kill the dead");

    userRepository.delete(found.get());
  }


  public void setPfp(String username, MultipartFile image) {
    checkPrincipal(username);

    File file = new File(pfp_path, username);
    try (FileOutputStream stream = new FileOutputStream(file)){
      stream.write(image.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void removePfp(String username) {
    checkPrincipal(username);

    File file = new File(pfp_path, username);
    try {
      Files.deleteIfExists(file.toPath());
    } catch (IOException e){
      throw new RuntimeException("could not remove pfp");
    }
  }

  public Resource getPfp(String username) {
    checkPrincipal(username);

    File file = new File(pfp_path, username);
    if (!file.exists()) throw new RuntimeException("no file found");

    return new FileSystemResource(file);
  }
}


