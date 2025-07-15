package com.proj.pampukh.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pampukh/user")
public class UserEndpoint {

  // TODO:

  @GetMapping("fafa")
  public ResponseEntity<String> ahha(){
    return ResponseEntity.ok("fafa\uD83E\uDD51");
  }

}
