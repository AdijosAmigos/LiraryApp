package com.example.LibraryApp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import com.example.LibraryApp.model.User;
import com.example.LibraryApp.model.dto.userdto.UserUpdateRequest;
import com.example.LibraryApp.repository.UserRepository;
import com.example.LibraryApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestRestTemplate testRestTemplate;

  private final String domain = "http://localhost:";

  private User user;

  @BeforeEach
  void addUser(){
    user = new User(1L, "testUser", "testPassword", "test@email.com", new ArrayList<>());
  }

  @Test
  void shouldAddUser(){
    var result = testRestTemplate.postForEntity(domain + port + "/users", user, User.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(201);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(User.class);
    assertThat(result.getBody()).isEqualTo(user);
  }

  @Test
  void shouldFindUserById(){
    User user2 = new User(2L, "testUser2", "testPassword2", "test@email.com2", new ArrayList<>());
    userRepository.save(user);
    userRepository.save(user2);

    var result = testRestTemplate.getForEntity(domain + port + "/users/2", User.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(User.class);
    assertThat(result.getBody()).isEqualTo(user2);
  }

  @Test
  void shouldFindAllUsers(){
    User user2 = new User(2L, "testUser2", "testPassword2", "test@email.com2", new ArrayList<>());
    userRepository.save(user);
    userRepository.save(user2);

    var result = testRestTemplate.getForEntity(domain + port + "/users", User[].class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(User[].class);
    assertThat(result.getBody()).contains(user, user2);
  }

  @Test
  void shouldDeleteUser(){
    User user2 = new User(2L, "testUser2", "testPassword2", "test@email.com2", new ArrayList<>());
    userRepository.save(user);
    userRepository.save(user2);

    testRestTemplate.delete(domain + port + "/users/delete/2");

    assertThat(userRepository.findAll().contains(user)).isTrue();
    assertThat(user2).isNotIn(userRepository.findAll());
  }

  @Test
  void shouldUpdateUser(){
    userRepository.save(user);

    UserUpdateRequest request = new UserUpdateRequest(
      "updatedName", "updatedPassword", "updated@email.com");
    HttpEntity<UserUpdateRequest> httpEntity = new HttpEntity<>(request);

    var result = testRestTemplate.exchange(
      domain + port + "/users/1", HttpMethod.PUT, httpEntity, User.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(User.class);
    assertThat(result.getBody().getName()).isEqualTo(httpEntity.getBody().getName());
    assertThat(result.getBody().getPassword()).isEqualTo(httpEntity.getBody().getPassword());
    assertThat(result.getBody().getEmail()).isEqualTo(httpEntity.getBody().getEmail());
  }




}