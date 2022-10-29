package com.example.LibraryApp.model.dto.userdto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class UserResponse {
  private Long id;
  private String name;
  private String password;
  private String email;
  private List<BookResponse> books;

  public UserResponse(Long id, String name, String password, String email, List<BookResponse> books) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.email = email;
    this.books = books;
  }


  @Data
  public static class BookResponse{
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private String ISBN;

    public BookResponse(Long id, String name, LocalDate releaseDate, String ISBN) {
      this.id = id;
      this.name = name;
      this.releaseDate = releaseDate;
      this.ISBN = ISBN;
    }
  }
}
