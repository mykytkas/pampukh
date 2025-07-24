package com.proj.pampukh.services;

import com.proj.pampukh.dto.library.LibraryDetailDto;
import com.proj.pampukh.dto.library.LibraryDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface LibraryService {

  LibraryDto create(LibraryDto libraryDto, MultipartFile libraryCover);

  LibraryDto update(LibraryDto updateDto, MultipartFile updateCover);

  void delete(String libraryName);

  LibraryDetailDto getLibraryData(String libraryName);

  Resource getLibraryCover(String libraryName);
}
