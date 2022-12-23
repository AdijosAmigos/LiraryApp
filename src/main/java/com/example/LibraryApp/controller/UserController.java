package com.example.LibraryApp.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.example.LibraryApp.model.User;
import com.example.LibraryApp.model.dto.userdto.UserCreateRequest;
import com.example.LibraryApp.model.dto.userdto.UserResponse;
import com.example.LibraryApp.model.dto.userdto.UserUpdateRequest;
import com.example.LibraryApp.repository.UserRepository;
import com.example.LibraryApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;
  private final UserService userService;

  @Autowired
  public UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponse> addUser(@RequestBody UserCreateRequest user){
    User createdUser = userService.addUser(user);
    return new ResponseEntity<>(toUserResponse(createdUser), HttpStatus.CREATED);
  }

  /*
  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers(){
    List<UserResponse> allUsers = userService.getUsers()
      .stream()
      .map(this::toUserResponse)
      .collect(Collectors.toList());
    return new ResponseEntity<>(allUsers, HttpStatus.OK);
  }
   */

  @GetMapping
  public String getAllUsers(Model model){
    List<UserResponse> allUsers = userService.getUsers()
      .stream()
      .map(this::toUserResponse)
      .toList();
    model.addAttribute("users", allUsers);
    return "users";
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
    if(id < 0){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return userService.findUserById(id)
      .map(this::toUserResponse)
      .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long id){
    if(id < 0){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    userService.deleteUserById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUserById(
    @RequestBody UserUpdateRequest student,
    @PathVariable Long id) {
    if(id < 0){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    User updatedUser = userService.updateUser(student, id);
    return new ResponseEntity<>(toUserResponse(updatedUser), HttpStatus.OK);
  }

  @PostMapping("/{userId}/{bookId}")
  public ResponseEntity<UserResponse> assignBookForUser(@PathVariable Long userId, @PathVariable Long bookId){
    if(userId < 0 || bookId < 0){
      new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    User userBorrowedBook = userService.borrowBook(userId, bookId);
    return new ResponseEntity<>(toUserResponse(userBorrowedBook), HttpStatus.OK);
  }

  private UserResponse toUserResponse(User user){
    List<UserResponse.BookResponse> books = user.getBooks().stream()
      .map(book -> new UserResponse.BookResponse(
        book.getId(),
        book.getName(),
        book.getReleaseDate(),
        book.getISBN()))
      .collect(Collectors.toList());
    return new UserResponse(user.getId(), user.getName(), user.getPassword(), user.getEmail(), books);
  }

  private UserCreateRequest toUserCreateRequest(User user){
    return new UserCreateRequest(
      user.getId(),
      user.getName(),
      user.getPassword(),
      user.getEmail(),
      user.getBooks()
    );
  }

}
