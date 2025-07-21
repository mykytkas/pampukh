package com.proj.pampukh.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libraries")
@Data
public class Library {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner")
  AppUser owner;

  String color;

  @Column(name = "cover_path")
  String coverPath;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "library_id")
  List<Fileref> filerefs = new ArrayList<>();

  public void setOwner(AppUser user) {
    user.getLibraries().add(this);
    this.owner = user;
  }
}
