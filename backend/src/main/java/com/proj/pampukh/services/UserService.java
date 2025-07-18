package com.proj.pampukh.services;

import com.proj.pampukh.dto.user.UserUpdateDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  String update(UserUpdateDto userDto);

  void delete(String username);

  void setPfp(String username, MultipartFile image);

  Resource getPfp(String username);

  void removePfp(String username);
}
