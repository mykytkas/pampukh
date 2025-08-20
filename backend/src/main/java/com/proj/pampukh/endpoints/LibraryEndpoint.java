package com.proj.pampukh.endpoints;

import com.proj.pampukh.dto.library.FileDataDto;
import com.proj.pampukh.dto.library.LibraryCreateDto;
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

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<LibraryDto> createLibrary(
      @RequestPart LibraryCreateDto toCreate,
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

  @DeleteMapping("/{libraryId}")
  public ResponseEntity<Void> deleteLibrary(
      @PathVariable("libraryId") Long libraryId) {
    libraryService.delete(libraryId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{libraryId}")
  public ResponseEntity<LibraryDetailDto> getLibrary(
      @PathVariable("libraryId") Long libraryId
  ) {
    LibraryDetailDto library = libraryService.getLibraryData(libraryId);
    return ResponseEntity.ok(library);
  }

  @GetMapping("/{libraryId}/cover")
  public ResponseEntity<Resource> getLibraryCover(
      @PathVariable("libraryId") Long libraryId
  ) {
    Resource cover = libraryService.getLibraryCover(libraryId);
    return ResponseEntity.ok(cover);
  }

  @PostMapping(value = "/{libraryId}/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<FileDataDto> uploadFile(
      @PathVariable("libraryId") Long libraryId,
      @RequestPart("data") FileDataDto fileDataDto,
      @RequestPart("file") MultipartFile file
  ) {
    var addedFile = libraryService.addFile(libraryId, fileDataDto, file);
    return ResponseEntity.ok(addedFile);
  }

  @DeleteMapping("/{libraryId}/files")
  public ResponseEntity<Void> removeFile(
    @PathVariable("libraryId") Long libraryId,
    @RequestParam String fileName
  ) {
    libraryService.removeFile(libraryId, fileName);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{libraryId}/files")
  public ResponseEntity<FileDataDto> getFileData(
      @PathVariable("libraryId") Long libraryId,
      @RequestParam String fileName
  ) {
    return ResponseEntity.ok(libraryService.getFileData(libraryId, fileName));
  }

  @GetMapping("/{libraryId}/files/resource")
  public ResponseEntity<Resource> getFileResource(
      @PathVariable("libraryId") Long libraryId,
      @RequestParam String fileName
  ) {
    return ResponseEntity.ok(libraryService.getFileResource(libraryId, fileName));
  }
}
