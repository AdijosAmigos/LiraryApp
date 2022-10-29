package com.example.LibraryApp.repository;

import com.example.LibraryApp.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
