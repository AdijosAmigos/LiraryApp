package com.example.LibraryApp.service;


import java.util.List;
import com.example.LibraryApp.model.User;
import com.example.LibraryApp.model.dto.userdto.UserCreateRequest;
import com.example.LibraryApp.model.dto.userdto.UserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest {

  @Autowired
  UserService userService;

  @Test
  void should_save_user() {
    //given
    UserCreateRequest user = new UserCreateRequest(
      1L, "Adrian",
      "password",
      "email@gmail.com");
    //when
    var result = userService.addUser(user);
    //then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(1L);
    Assertions.assertThat(result.getName()).isEqualTo(user.getName());
    Assertions.assertThat(result.getPassword()).isEqualTo(user.getPassword());
    Assertions.assertThat(result.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  void should_find_all_users() {
    //given
    UserCreateRequest user = new UserCreateRequest(
      1L, "Adrian",
      "password",
      "email@gmail.com");
    //when
    userService.addUser(user);
    var result  = userService.getUsers();
    //then
    Assertions.assertThat(result.stream().findAny()).isPresent();
  }

  @Test
  void should_find_user_by_id() {
    //given
    UserCreateRequest user = new UserCreateRequest(
      1L, "Adrian",
      "password",
      "email@gmail.com");
    //when
    userService.addUser(user);
    var result  = userService.findUserById(user.getId());
    //then
    Assertions.assertThat(result.stream().findFirst()).isPresent();

  }

}