package com.example.LibraryApp.model.dto.userdto;

import java.util.List;
import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.User;
import lombok.Data;

@Data
public class UserCreateRequest {
  private Long id;
  private String name;
  private String password;
  private String email;
  private List<Book> books;

  public UserCreateRequest(Long id, String name, String password, String email, List<Book> books) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.email = email;
    this.books = books;
  }

  public static User fromDto(UserCreateRequest user){
    return new User(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getBooks());
  }
}
