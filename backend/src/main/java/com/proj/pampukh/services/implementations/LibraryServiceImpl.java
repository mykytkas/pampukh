package com.proj.pampukh.services.implementations;

import com.proj.pampukh.dto.library.LibraryDetailDto;
import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.mappers.LibraryDtoMapper;
import com.proj.pampukh.persistence.AppUserRepository;
import com.proj.pampukh.persistence.LibraryRepository;
import com.proj.pampukh.persistence.entity.AppUser;
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
import java.util.Optional;

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

  public LibraryDto create(LibraryDto libraryDto, MultipartFile libraryCover) {
    AppUser user = principal();
    // Entity
    Library library = new Library();
    library.setName(libraryDto.name());
    library.setColor(libraryDto.color());
    library.setOwner(user);

    // cover file
    if (!libraryCover.isEmpty()) {
      saveCover(library, libraryCover);
    }

    libraryRepo.save(library);
    return mapper.mapToDto(library);
  }

  private void saveCover(Library library, MultipartFile libraryCover) {
    // cover file
    String coverName = libraryCover.getOriginalFilename();
    File file = new File(coverFolder, coverName);

    try (FileOutputStream stream = new FileOutputStream(file)){
      stream.write(libraryCover.getBytes());
      library.setCoverPath(coverName);
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
      saveCover(library, updateCover);
    }

    libraryRepo.save(library);
    return mapper.mapToDto(library);
  }

  @Override
  public void delete(String libraryName) {
    Library library = libraryRepo.findLibraryByName(libraryName);
    if (library == null) {
      throw  new RuntimeException("i want to write custom extensions, but im so lazy");
    }
    libraryRepo.delete(library);

    File cover = new File(coverFolder, library.getCoverPath());
    try {
      Files.deleteIfExists(cover.toPath());
    } catch (IOException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public LibraryDetailDto getLibraryData(String libraryName) {
    Library library = libraryRepo.findLibraryByName(libraryName);
    if (library == null) throw new RuntimeException("ughghg");

    return mapper.mapToDetailDto(library);
  }

  @Override
  public Resource getLibraryCover(String libraryName) {
    Library library = libraryRepo.findLibraryByName(libraryName);
    if (library == null) throw new RuntimeException("ughghg");

    File cover = new File(coverFolder, library.getCoverPath());
    if (!cover.exists()) return null;//?? what is default behaviour??

    return new FileSystemResource(cover);
  }


}
