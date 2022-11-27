package com.example.LibraryApp.model.dto.userdto;

import lombok.Data;

@Data
public class UserUpdateRequest {
  private String name;
  private String password;
  private String email;

  public UserUpdateRequest(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.email = email;
  }
}
