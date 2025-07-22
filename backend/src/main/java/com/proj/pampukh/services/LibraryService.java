package com.proj.pampukh.services;

import com.proj.pampukh.dto.library.LibraryDto;
import org.springframework.web.multipart.MultipartFile;

public interface LibraryService {

  public LibraryDto create(LibraryDto libraryDto, MultipartFile libraryCover);
}
