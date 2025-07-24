package com.proj.pampukh.endpoints;

import com.proj.pampukh.dto.library.LibraryDetailDto;
import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.services.LibraryService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<LibraryDto> updateLibrary(
      @RequestPart LibraryDto toUpdate,
      @RequestPart("file") MultipartFile cover) {
    LibraryDto library = libraryService.update(toUpdate, cover);
    return ResponseEntity.ok(library);
  }

  @DeleteMapping("/{libraryName}")
  public ResponseEntity<Void> deleteLibrary(
      @PathVariable("libraryName") String libraryName) {
    libraryService.delete(libraryName);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{libraryName}")
  public ResponseEntity<LibraryDetailDto> getLibrary (
      @PathVariable("libraryName") String libraryName
  ) {
    LibraryDetailDto library = libraryService.getLibraryData(libraryName);
    return ResponseEntity.ok(library);
  }

  @GetMapping("/{libraryName}/cover")
  public ResponseEntity<Resource> getLibraryCover (
  @PathVariable("libraryName") String libraryName
  ) {
    Resource cover = libraryService.getLibraryCover(libraryName);
    return ResponseEntity.ok(cover);
  }
}
