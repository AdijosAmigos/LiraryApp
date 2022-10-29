package com.example.LibraryApp.service;

import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.User;
import com.example.LibraryApp.model.dto.userdto.UserCreateRequest;
import com.example.LibraryApp.model.dto.userdto.UserUpdateRequest;
import com.example.LibraryApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookService bookService;

    @Autowired
    public UserService(UserRepository userRepository, BookService bookService) {
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    public User addUser(UserCreateRequest user) {
        User createdStudent = new User(
          user.getId(),
          user.getName(),
          user.getPassword(),
          user.getEmail(),
          user.getBooks());
        return userRepository.save(createdStudent);
    }

    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll()
                .spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User deleteUserById(Long id) {
       User user = findUserById(id).orElseThrow();
       userRepository.delete(user);
       return user;
    }

    public User updateUser(UserUpdateRequest user, Long id) {
        User editUser = findUserById(id).orElseThrow();
        editUser.setEmail(user.getEmail());
        editUser.setName(user.getName());
        editUser.setPassword(user.getPassword());
        return userRepository.save(editUser);
    }

    public void borrowBook(Long bookId, Long userId){
        Book book = bookService.findBookById(bookId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        user.addBook(book);
        userRepository.save(user);
    }

}
