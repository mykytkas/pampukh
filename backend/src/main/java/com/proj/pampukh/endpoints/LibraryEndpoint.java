package com.proj.pampukh.endpoints;

import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.services.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pampukh/library")
public class LibraryEndpoint {

  private final LibraryService libraryService;

  public LibraryEndpoint(LibraryService libraryService) {
    this.libraryService = libraryService;
  }

  @PostMapping
  public ResponseEntity<LibraryDto> createLibrary(@RequestBody LibraryDto toCreate) {
    LibraryDto library = libraryService.create(toCreate);
    return ResponseEntity.ok(library);
  }
}
