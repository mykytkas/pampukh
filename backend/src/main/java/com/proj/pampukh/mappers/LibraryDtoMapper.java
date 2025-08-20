package com.proj.pampukh.mappers;

import com.proj.pampukh.dto.library.LibraryDetailDto;
import com.proj.pampukh.dto.library.LibraryDto;
import com.proj.pampukh.persistence.entity.Fileref;
import com.proj.pampukh.persistence.entity.Library;
import org.springframework.stereotype.Component;

@Component
public class LibraryDtoMapper {

  public LibraryDto mapToDto(Library library) {
    return new LibraryDto(
        library.getId(),
        library.getName(),
        library.getColor()
    );
  }

  public LibraryDetailDto mapToDetailDto(Library library) {
    return new LibraryDetailDto(
        library.getId(),
        library.getName(),
        library.getColor(),
        library.getFilerefs().stream().map(Fileref::getName).toList()
    );
  }
}
