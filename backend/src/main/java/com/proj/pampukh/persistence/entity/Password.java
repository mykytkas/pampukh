package com.proj.pampukh.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "passwords")
public class Password {

  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  private AppUser user;

  private String hash;
}
