package com.proj.pampukh.services.implementations;

import com.proj.pampukh.dto.user.UserLoginDto;
import com.proj.pampukh.dto.user.UserRegisterDto;
import com.proj.pampukh.persistence.AppUserRepository;
import com.proj.pampukh.persistence.entity.AppUser;
import com.proj.pampukh.persistence.entity.Password;
import com.proj.pampukh.security.JwtUtil;
import com.proj.pampukh.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

  private final AppUserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;


  public AuthServiceImpl(AppUserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * here the authentication 'takes place'
   */
  private Authentication buildAuthentication(String username, String password) {
    return authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }

  public String login(UserLoginDto userDto) {
    System.out.println(userDto);
    // TODO: move validation of data to separate validator class
    if (userRepository.findAppUserByUsername(userDto.username()).isEmpty()) {
      // TODO: define custom exceptions
      throw new RuntimeException("no such user to login");
    }

    Authentication authentication = buildAuthentication(
        userDto.username(),
        userDto.password());

    return jwtUtil.generateAuthToken(authentication);
  }

  public String register(UserRegisterDto userDto) {
    AppUser user = new AppUser();
    user.setUsername(userDto.name());

    Password password = new Password();
    password.setHash(passwordEncoder.encode(userDto.password()));
    password.setUser(user);
    user.setPassword(password);

    userRepository.save(user);

    Authentication authentication = buildAuthentication(
        userDto.name(), userDto.password());

    return jwtUtil.generateAuthToken(authentication);
  }


}
