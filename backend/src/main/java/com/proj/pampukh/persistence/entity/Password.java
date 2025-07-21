package com.proj.pampukh.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "passwords")
@Data
public class Password {

  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  private AppUser user;

  private String hash;
}
