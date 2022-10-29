package com.example.LibraryApp.service;

import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.dto.bookdto.BookCreateRequest;
import com.example.LibraryApp.model.dto.bookdto.BookUpdateRequest;
import com.example.LibraryApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(BookCreateRequest book){
        Book createdBook = new Book(
          book.getId(),
          book.getName(),
          book.getReleaseDate(),
          book.getISBN());
        return bookRepository.save(createdBook);
    }

    public List<Book> getBooks() {
        return StreamSupport.stream(bookRepository.findAll()
                .spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Book> findBookById(Long id){
        return bookRepository.findById(id);
    }

    public Book deleteBookById(Long id){
        Book book = findBookById(id).orElseThrow();
        bookRepository.delete(book);
        return book;
    }

    public Book updateBook(BookUpdateRequest book, Long id){
        Book editBook = findBookById(id).orElseThrow();
        editBook.setName(book.getName());
        editBook.setReleaseDate(book.getReleaseDate());
        editBook.setISBN(book.getISBN());
        return bookRepository.save(editBook);
    }
}
