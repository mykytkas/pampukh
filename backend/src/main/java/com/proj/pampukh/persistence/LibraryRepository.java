package com.proj.pampukh.persistence;

import com.proj.pampukh.persistence.entity.AppUser;
import com.proj.pampukh.persistence.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface LibraryRepository extends JpaRepository<Library, Long> {

  Library findLibraryByName(String name);

  List<Library> findLibrariesByOwner(AppUser owner);
}
