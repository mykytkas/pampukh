package com.proj.pampukh.dto.library;

import java.util.List;

public record LibraryDetailDto(
    Long id,
    String name,
    String color,
    List<String> filenames
) {
}
