package com.example.LibraryApp.service;

import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.User;
import com.example.LibraryApp.model.dto.userdto.UserCreateRequest;
import com.example.LibraryApp.model.dto.userdto.UserUpdateRequest;
import com.example.LibraryApp.repository.BookRepository;
import com.example.LibraryApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
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
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id) {
       userRepository.deleteById(id);
    }

    public User updateUser(UserUpdateRequest user, Long id) {
        User editUser = findUserById(id).orElseThrow();
        editUser.setEmail(user.getEmail());
        editUser.setName(user.getName());
        editUser.setPassword(user.getPassword());
        return userRepository.save(editUser);
    }

    public User borrowBook(Long userId, Long bookId){
        var book = bookRepository.getById(bookId);
        var user = userRepository.getById(userId);
        user.addBook(book);
        return userRepository.save(user);
    }

}
