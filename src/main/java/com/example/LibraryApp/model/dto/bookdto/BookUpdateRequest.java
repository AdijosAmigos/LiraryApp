package com.example.LibraryApp.model.dto.bookdto;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Data
public class BookUpdateRequest {
  private String name;
  private LocalDate releaseDate;
  private String ISBN;

  public BookUpdateRequest(String name, LocalDate releaseDate, String ISBN) {
    this.name = name;
    this.releaseDate = releaseDate;
    this.ISBN = ISBN;
  }
}
