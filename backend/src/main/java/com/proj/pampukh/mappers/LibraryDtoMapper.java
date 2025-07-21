package com.proj.pampukh.mappers;

import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.persistence.entity.Library;
import org.springframework.stereotype.Component;

@Component
public class LibraryDtoMapper {

  public LibraryDto mapToDto(Library library) {
    return new LibraryDto(library.getName(), library.getColor());
  }
}
