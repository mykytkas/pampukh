package com.proj.pampukh.services;

import com.proj.pampukh.dto.user.UserLoginDto;
import com.proj.pampukh.dto.user.UserRegisterDto;

/**
 * Service responsible for registering and logging-in-ing users,
 * authenticating (in case of login),
 * and generating and returning a new jwt token.
 */
public interface AuthService {

  /**
   * Logs in a user, if the password and username are correct.
   *
   * @param userDto data of user to be logged in
   * @return updated jwt token for user, if credentials are correct
   */
  String login(UserLoginDto userDto);

  /**
   * Registers a new user in system, with given data.
   *
   * @param userDto data of the user to be registered
   * @return jwt token for user
   */
  String register(UserRegisterDto userDto);
}
