package com.proj.pampukh.services.implementations;

import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.mappers.LibraryDtoMapper;
import com.proj.pampukh.persistence.AppUserRepository;
import com.proj.pampukh.persistence.LibraryRepository;
import com.proj.pampukh.persistence.entity.AppUser;
import com.proj.pampukh.persistence.entity.Library;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryServiceImpl {


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

  public LibraryDto create(LibraryDto libraryDto) {
    AppUser user = principal();

    Library library = new Library();
    library.setName(libraryDto.name());
    library.setColor(libraryDto.color());
    library.setOwner(user);
    libraryRepo.save(library);

    return mapper.mapToDto(library);
  }

}
