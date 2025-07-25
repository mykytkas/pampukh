package com.proj.pampukh.services;

import com.proj.pampukh.dto.library.FileDataDto;
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

  FileDataDto addFile(String libraryName, FileDataDto fileDto, MultipartFile file);

  void removeFile(String libraryName, String fileName);

  FileDataDto getFileData(String libraryName, String fileName);

  Resource getFileResource(String libraryName, String fileName);
}
