package com.proj.pampukh.services;

import com.proj.pampukh.dto.library.FileDataDto;
import com.proj.pampukh.dto.library.LibraryCreateDto;
import com.proj.pampukh.dto.library.LibraryDetailDto;
import com.proj.pampukh.dto.library.LibraryDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LibraryService {

  LibraryDto create(LibraryCreateDto libraryDto, MultipartFile libraryCover);

  LibraryDto update(LibraryDto updateDto, MultipartFile updateCover);

  void delete(Long libraryId);

  List<LibraryDto> getLibraryList();

  LibraryDetailDto getLibraryData(Long libraryId);

  List<Resource> getAllLibraryCovers();

  Resource getLibraryCover(Long libraryId);

  FileDataDto addFile(Long libraryId, FileDataDto fileDto, MultipartFile file);

  void removeFile(Long libraryId, String fileName);

  FileDataDto getFileData(Long libraryId, String fileName);

  Resource getFileResource(Long libraryId, String fileName);
}
