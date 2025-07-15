package com.proj.pampukh.services;

import com.proj.pampukh.persistence.AppUserRepository;
import com.proj.pampukh.persistence.entity.AppUser;
import com.proj.pampukh.security.AuthUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailsService implements UserDetailsService {


  private final AppUserRepository userRepository;

  public AuthUserDetailsService(AppUserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AppUser> appUserOptional = userRepository.findAppUserByUsername(username);
    if (appUserOptional.isEmpty()) throw new UsernameNotFoundException("no such user");
    return new AuthUserDetails(appUserOptional.get());

  }
}
