package com.proj.pampukh.dto.library;

import java.util.List;

public record LibraryDetailDto(
    String name,
    String color,
    List<String> filenames
) {
}
