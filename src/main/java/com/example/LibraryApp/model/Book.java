package com.example.LibraryApp.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;
    private String name;

    private LocalDate releaseDate;
    private String ISBN;
    private String authors;
    @ManyToOne
    private User users;


    public Book() {
    }

    public Book(Long id, String name, LocalDate releaseDate, String ISBN, User users) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.ISBN = ISBN;
        this.users = users;
    }

    public Book(Long id, String name, LocalDate releaseDate, String ISBN) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.ISBN = ISBN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public User getUser() {
        return users;
    }

    public void setUser(User user) {
        this.users = user;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthors() {
        return authors;
    }
    public void setAuthors(String authors) {
        this.authors = authors;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id)
                && Objects.equals(name, book.name)
                && Objects.equals(releaseDate, book.releaseDate)
                && Objects.equals(ISBN, book.ISBN)
                && Objects.equals(users, book.users)
                && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseDate, ISBN, users, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", ISBN='" + ISBN + '\'' +
                ", user=" + users + '\'' +
                ", authors=" + authors +
                '}';
    }
}
