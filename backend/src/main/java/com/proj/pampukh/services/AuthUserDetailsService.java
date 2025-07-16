package com.proj.pampukh.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Custom UserDetailsService implementation.
 * (such bean is created and used as a provider* for the Authentication)
 */
public interface AuthUserDetailsService extends UserDetailsService {

  @Override
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
