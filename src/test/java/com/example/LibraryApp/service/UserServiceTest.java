package com.example.LibraryApp.service;
import java.time.LocalDate;
import java.util.ArrayList;
import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.User;
import com.example.LibraryApp.model.dto.userdto.UserCreateRequest;
import com.example.LibraryApp.model.dto.userdto.UserUpdateRequest;
import com.example.LibraryApp.repository.BookRepository;
import com.example.LibraryApp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest {
  @Autowired
  UserService userService;
  @Autowired
  UserRepository userRepository;
  @Autowired
  BookRepository bookRepository;

  UserCreateRequest user;

  @BeforeEach
  void should_create_user(){
    user = new UserCreateRequest(1L, "Adrian",
      "password",
      "email@gmail.com", new ArrayList<>());
  }

  @Test
  void should_save_user() {
    //when
    var result = userService.addUser(user);
    //then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(user.getId());
    Assertions.assertThat(result.getName()).isEqualTo(user.getName());
    Assertions.assertThat(result.getPassword()).isEqualTo(user.getPassword());
    Assertions.assertThat(result.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  void should_find_all_users() {
    //when
    userService.addUser(user);
    var result  = userService.getUsers();
    //then
    Assertions.assertThat(result.stream().findAny()).isPresent();
  }

  @Test
  void should_find_user_by_id() {
    //when
    userService.addUser(user);
    var result  = userService.findUserById(user.getId());
    //then
    Assertions.assertThat(result.stream().findFirst()).isPresent();
  }

  @Test
  void should_delete_user_by_id(){
    //when
    userService.addUser(user);
    userService.findUserById(user.getId());
    userService.deleteUserById(user.getId());
    //then
    Assertions.assertThat(userRepository.findAll()).isEmpty();
  }

  @Test
  void should_update_user(){
    //given
    UserUpdateRequest updatedUser = new UserUpdateRequest(
      "Maciek", "maciekpassowrd", "maciek@gamil.com"
    );
    //when
    userService.addUser(user);
    var result = userService.updateUser(updatedUser, user.getId());
    //then
    Assertions.assertThat(result.getName()).isEqualTo(updatedUser.getName());
    Assertions.assertThat(result.getPassword()).isEqualTo(updatedUser.getPassword());
    Assertions.assertThat(result.getEmail()).isEqualTo(updatedUser.getEmail());
  }

  @Test
  void should_borrow_book(){
    //given
    Book book = new Book(2L, "testBook", LocalDate.now(), "testISBN", "testAuthor");
    //when
    var savedUser = userRepository.save(UserCreateRequest.fromDto(user));
    var savedBook = bookRepository.save(book);
    User userWithResources = userService.borrowBook(savedUser.getId(), savedBook.getId());
    //then
    Assertions.assertThat(userWithResources.getBooks().stream().findFirst()).isPresent();
  }


}