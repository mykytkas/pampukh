package com.proj.pampukh.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "filerefs")
@Data
public class Fileref {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String path;

  String name;

}
