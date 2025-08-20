package com.proj.pampukh.services.implementations;

import com.proj.pampukh.dto.library.FileDataDto;
import com.proj.pampukh.dto.library.LibraryCreateDto;
import com.proj.pampukh.dto.library.LibraryDetailDto;
import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.mappers.LibraryDtoMapper;
import com.proj.pampukh.persistence.AppUserRepository;
import com.proj.pampukh.persistence.LibraryRepository;
import com.proj.pampukh.persistence.entity.AppUser;
import com.proj.pampukh.persistence.entity.Fileref;
import com.proj.pampukh.persistence.entity.Library;
import com.proj.pampukh.services.LibraryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class LibraryServiceImpl implements LibraryService {

  @Value("${global.covers}")
  private String coverFolder;

  @Value("${global.files}")
  private String fileFolder;
  private final LibraryRepository libraryRepo;
  private final AppUserRepository userRepository;
  private final LibraryDtoMapper mapper;

  public LibraryServiceImpl(LibraryRepository libraryRepo,
                            AppUserRepository userRepository,
                            LibraryDtoMapper mapper) {
    this.libraryRepo = libraryRepo;
    this.userRepository = userRepository;
    this.mapper = mapper;
  }

  private AppUser principal() {
    String principle = SecurityContextHolder.getContext()
        .getAuthentication().getName();
    Optional<AppUser> user = userRepository.findAppUserByUsername(principle);

    if (user.isEmpty()) throw new RuntimeException("oh nooo");

    return user.get();
  }

  public LibraryDto create(LibraryCreateDto libraryDto, MultipartFile libraryCover) {
    AppUser user = principal();
    // Entity
    Library library = new Library();
    library.setName(libraryDto.name());
    library.setColor(libraryDto.color());
    library.setOwner(user);

    // cover file
    if (!libraryCover.isEmpty()) {
      saveFile(coverFolder, libraryCover);
      library.setCoverPath(libraryCover.getOriginalFilename());
    }

    library = libraryRepo.save(library);

    //setup central storage for files
    setupLibraryFolder(library.getId());

    return mapper.mapToDto(library);
  }

  // can and will break from special characters in library's username (both set and get)
  //TODO: make implementation more robust or write validation
  private void setupLibraryFolder(Long libraryId) {
    File folder = new File(fileFolder, libraryId.toString());
    if (!folder.mkdir())
      throw new RuntimeException(">>> can't create a folder for " + libraryId);
  }

  private String getLibraryFolder(Long libraryId) {
    return fileFolder + "/" + libraryId;
  }


  private void saveFile(String folderName, MultipartFile toSave) {
    File file = new File(folderName, toSave.getOriginalFilename());
    try (FileOutputStream stream = new FileOutputStream(file)) {
      stream.write(toSave.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public LibraryDto update(LibraryDto updateDto, MultipartFile updateCover) {
    // probably not needed
    Library library = libraryRepo.findLibraryByName(updateDto.name());
    if (library == null) {
      throw  new RuntimeException("i want to write custom extensions, but im so lazy");
    }
    library.setColor(updateDto.color());

    if (!updateCover.isEmpty()) {
      saveFile(coverFolder, updateCover);
      library.setCoverPath(updateCover.getOriginalFilename());
    }

    libraryRepo.save(library);
    return mapper.mapToDto(library);
  }

  @Override
  public void delete(Long libraryId) {
    Optional<Library> library = libraryRepo.findById(libraryId);
    if (library.isEmpty()) {
      throw new RuntimeException("i want to write custom extensions, but im so lazy");
    }
    libraryRepo.delete(library.get());

    File cover = new File(coverFolder, library.get().getCoverPath());
    try {
      Files.deleteIfExists(cover.toPath());
    } catch (IOException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<LibraryDto> getLibraryList() {
    List<LibraryDto> libraries;

    libraries = libraryRepo.findLibrariesByOwner(principal())
        .stream()
        .map(
            entity -> new LibraryDto(entity.getId(), entity.getName(), entity.getColor())
        ).toList();

    return libraries;
  }

  @Override
  public LibraryDetailDto getLibraryData(Long libraryId) {
    Optional<Library> library = libraryRepo.findById(libraryId);
    if (library.isEmpty()) throw new RuntimeException("ughghg");

    return mapper.mapToDetailDto(library.get());
  }

  @Override
  public List<Resource> getAllLibraryCovers() {
    List<Library> libraries = libraryRepo.findLibrariesByOwner(principal());
    List<Resource> resources = new ArrayList<>();
    libraries
        .forEach(lib
            -> resources.add(new FileSystemResource(
            new File(coverFolder, lib.getCoverPath())))
        );
    return resources;
  }

  @Override
  public Resource getLibraryCover(Long libraryId) {
    Optional<Library> library = libraryRepo.findById(libraryId);
    if (library.isEmpty()) throw new RuntimeException("ughghg");

    File cover = new File(coverFolder, library.get().getCoverPath());
    if (!cover.exists()) return null;//?? what is default behaviour??

    return new FileSystemResource(cover);
  }

  @Override
  public FileDataDto addFile(Long libraryId, FileDataDto fileDto, MultipartFile file) {
    Optional<Library> library = libraryRepo.findById(libraryId);
    if (library.isEmpty()) throw new RuntimeException("ughghg");

    Fileref fileref = new Fileref();
    fileref.setName(fileDto.name());
    fileref.setPath(file.getOriginalFilename());

    if (file.isEmpty()) throw new RuntimeException("why?");
    saveFile(getLibraryFolder(libraryId), file);

    libraryRepo.save(library.get());
    return new FileDataDto(fileDto.name());
  }

  @Override
  public void removeFile(Long libraryId, String fileName) {
    Optional<Library> library = libraryRepo.findById(libraryId);
    if (library.isEmpty()) throw new RuntimeException("ughghg");

    File cover = new File(getLibraryFolder(libraryId), fileName);
    try {
      Files.deleteIfExists(cover.toPath());
    } catch (IOException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public FileDataDto getFileData(Long libraryId, String fileName) {
    Optional<Library> library = libraryRepo.findById(libraryId);
    if (library.isEmpty()) throw new RuntimeException("ughghg");

    // ugly af
    return new FileDataDto(
        library.get().getFilerefs().stream()
            .filter(
                fileref -> fileref.getName().equals(fileName))
            .findFirst().map(Fileref::getName).orElseThrow()
    );
  }

  @Override
  public Resource getFileResource(Long libraryId, String fileName) {
    File file = new File(getLibraryFolder(libraryId), fileName);
    if (!file.exists()) throw new RuntimeException("library " + libraryId + "does not have file " + fileName);
    return new FileSystemResource(file);
  }


}
