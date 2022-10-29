package com.example.LibraryApp.model.dto.bookdto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class BookResponse {
  private Long id;
  private String name;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate releaseDate;
  private String ISBN;

  public BookResponse(Long id, String name, LocalDate releaseDate, String ISBN) {
    this.id = id;
    this.name = name;
    this.releaseDate = releaseDate;
    this.ISBN = ISBN;
  }

  @Data
  public static class UserResponse {
    private Long id;
    private String name;
    private String password;
    private String email;

    public UserResponse(Long id, String name, String password, String email) {
      this.id = id;
      this.name = name;
      this.password = password;
      this.email = email;
    }

  }
}
