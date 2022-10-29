package com.example.LibraryApp.model.dto.bookdto;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class BookCreateRequest {

  private Long id;
  private String name;
  private LocalDate releaseDate;
  private String ISBN;
  private String authors;

  public BookCreateRequest(Long id, String name, LocalDate releaseDate, String ISBN, String authors) {
    this.id = id;
    this.name = name;
    this.releaseDate = releaseDate;
    this.ISBN = ISBN;
    this.authors = authors;
  }
}
