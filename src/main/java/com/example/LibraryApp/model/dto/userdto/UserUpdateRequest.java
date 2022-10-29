package com.example.LibraryApp.model.dto.userdto;

import lombok.Data;

@Data
public class UserUpdateRequest {
  private Long id;
  private String name;
  private String password;
  private String email;

  public UserUpdateRequest(Long id, String name, String password, String email) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.email = email;
  }
}
