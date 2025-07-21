package com.proj.pampukh.persistence;

import com.proj.pampukh.persistence.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibraryRepository extends JpaRepository<Library, Long> {

  Library findLibraryByName(String name);

}
