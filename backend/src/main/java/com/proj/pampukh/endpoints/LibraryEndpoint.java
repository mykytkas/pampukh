package com.proj.pampukh.endpoints;

import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.services.LibraryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pampukh/library")
public class LibraryEndpoint {

  private final LibraryService libraryService;

  public LibraryEndpoint(LibraryService libraryService) {
    this.libraryService = libraryService;
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<LibraryDto> createLibrary(
      @RequestPart LibraryDto toCreate,
      @RequestPart("file") MultipartFile cover) {
    LibraryDto library = libraryService.create(toCreate, cover);
    return ResponseEntity.ok(library);
  }
}
