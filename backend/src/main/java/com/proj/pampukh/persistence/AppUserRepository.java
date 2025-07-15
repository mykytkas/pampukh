package com.proj.pampukh.persistence;

import com.proj.pampukh.persistence.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findAppUserByUsername(String username);
}
