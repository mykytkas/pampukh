package com.proj.pampukh.endpoints;

import com.proj.pampukh.dto.library.FileDataDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pampukh/library")
public class LibraryEndpoint {

  private final LibraryService libraryService;

  public LibraryEndpoint(LibraryService libraryService) {
    this.libraryService = libraryService;
  }

  @GetMapping()
  public ResponseEntity<List<LibraryDto>> getAllLibraries() {
    return ResponseEntity.ok(libraryService.getLibraryList());
  }

  @GetMapping("/covers")
  public ResponseEntity<List<Resource>> getAllLibraryCovers() {
    return ResponseEntity.ok(libraryService.getAllLibraryCovers());
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<LibraryDto> createLibrary(
      @RequestPart LibraryDto toCreate,
      @RequestPart(value = "file", required = false) MultipartFile cover) {
    LibraryDto library = libraryService.create(toCreate, cover);
    return ResponseEntity.ok(library);
  }

  @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<LibraryDto> updateLibrary(
      @RequestPart LibraryDto toUpdate,
      @RequestPart(value = "file", required = false) MultipartFile cover) {
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
  public ResponseEntity<LibraryDetailDto> getLibrary(
      @PathVariable("libraryName") String libraryName
  ) {
    LibraryDetailDto library = libraryService.getLibraryData(libraryName);
    return ResponseEntity.ok(library);
  }

  @GetMapping("/{libraryName}/cover")
  public ResponseEntity<Resource> getLibraryCover(
      @PathVariable("libraryName") String libraryName
  ) {
    Resource cover = libraryService.getLibraryCover(libraryName);
    return ResponseEntity.ok(cover);
  }

  @PostMapping(value = "/{libraryName}/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<FileDataDto> uploadFile(
      @PathVariable("libraryName") String libraryName,
      @RequestPart("data") FileDataDto fileDataDto,
      @RequestPart("file") MultipartFile file
  ) {
    var addedFile = libraryService.addFile(libraryName, fileDataDto, file);
    return ResponseEntity.ok(addedFile);
  }

  @DeleteMapping("/{libraryName}/files")
  public ResponseEntity<Void> removeFile(
    @PathVariable("libraryName") String libraryName,
    @RequestParam String fileName
  ) {
    libraryService.removeFile(libraryName, fileName);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{libraryName}/files")
  public ResponseEntity<FileDataDto> getFileData(
      @PathVariable("libraryName") String libraryName,
      @RequestParam String fileName
  ) {
    return ResponseEntity.ok(libraryService.getFileData(libraryName, fileName));
  }

  @GetMapping("/{libraryName}/files/resource")
  public ResponseEntity<Resource> getFileResource(
      @PathVariable("libraryName") String libraryName,
      @RequestParam String fileName
  ) {
    return ResponseEntity.ok(libraryService.getFileResource(libraryName, fileName));
  }
}
