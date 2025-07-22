package com.proj.pampukh.services.implementations;

import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.mappers.LibraryDtoMapper;
import com.proj.pampukh.persistence.AppUserRepository;
import com.proj.pampukh.persistence.LibraryRepository;
import com.proj.pampukh.persistence.entity.AppUser;
import com.proj.pampukh.persistence.entity.Library;
import com.proj.pampukh.services.LibraryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    String coverName = libraryCover.getOriginalFilename();
    File file = new File(coverFolder, coverName);
    try (FileOutputStream stream = new FileOutputStream(file)){
      stream.write(libraryCover.getBytes());
      library.setCoverPath(coverName);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    libraryRepo.save(library);
    return mapper.mapToDto(library);
  }


}
