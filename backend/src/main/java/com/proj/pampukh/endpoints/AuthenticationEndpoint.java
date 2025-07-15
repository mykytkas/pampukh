package com.proj.pampukh.endpoints;

import com.proj.pampukh.dto.user.UserLoginDto;
import com.proj.pampukh.dto.user.UserRegisterDto;
import com.proj.pampukh.services.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pampukh")
public class AuthenticationEndpoint {

  private final AuthServiceImpl authService;

  public AuthenticationEndpoint(AuthServiceImpl authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto) {
    return ResponseEntity.ok(authService.register(userRegisterDto));
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
    return ResponseEntity.ok(authService.login(userLoginDto));
  }
}
