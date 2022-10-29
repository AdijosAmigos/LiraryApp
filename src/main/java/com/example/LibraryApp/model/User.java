package com.example.LibraryApp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.example.LibraryApp.model.dto.userdto.UserCreateRequest;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    private String email;
    @OneToMany(
            mappedBy = "users",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Book> books;

    public User() {
    }

    public User(Long id, String name, String password, String email, List<Book> books) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.books = new ArrayList<>();
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static User fromDto(UserCreateRequest userCreateRequest){
        return new User(
          userCreateRequest.getId(),
          userCreateRequest.getName(),
          userCreateRequest.getPassword(),
          userCreateRequest.getEmail(),
          userCreateRequest.getBooks());
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book){
        books.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(name, user.name)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email)
                && Objects.equals(books, user.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, books);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", books='" + books + '\'' +
                '}';
    }
}
