package com.proj.pampukh.endpoints;

import com.proj.pampukh.dto.user.JwtResponse;
import com.proj.pampukh.dto.user.UserUpdateDto;
import com.proj.pampukh.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pampukh/user")
public class UserEndpoint {

  private final UserService userService;

  public UserEndpoint(UserService userService) {
    this.userService = userService;
  }

  @PutMapping()
  public ResponseEntity<JwtResponse> updateUserInfo(@RequestBody UserUpdateDto userDto) {
    return ResponseEntity.ok(new JwtResponse(userService.update(userDto)));
  }

  @DeleteMapping()
  public ResponseEntity<Void> deleteUser(@RequestBody String username) {
    userService.delete(username);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{username}/pfp")
  public ResponseEntity<Void> setProfilePicture(
      @PathVariable("username") String username,
      @RequestPart("file") MultipartFile image) {
    userService.setPfp(username, image);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{username}/pfp")
  public ResponseEntity<Resource> getProfilePicture(@PathVariable("username") String username) {
    return ResponseEntity.ok(userService.getPfp(username));
  }

  @DeleteMapping("/{username}/pfp")
  public ResponseEntity<Void> removeProfilePicture(@PathVariable("username") String username) {
    userService.removePfp(username);
    return ResponseEntity.ok().build();
  }

}
