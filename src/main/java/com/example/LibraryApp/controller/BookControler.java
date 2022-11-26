package com.example.LibraryApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.dto.bookdto.BookCreateRequest;
import com.example.LibraryApp.model.dto.bookdto.BookResponse;
import com.example.LibraryApp.model.dto.bookdto.BookUpdateRequest;
import com.example.LibraryApp.model.dto.userdto.UserResponse;
import com.example.LibraryApp.repository.BookRepository;
import com.example.LibraryApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookControler {

  private final BookRepository bookRepository;
  private final BookService bookService;

  @Autowired
  public BookControler(BookRepository bookRepository, BookService bookService) {
    this.bookRepository = bookRepository;
    this.bookService = bookService;
  }

  @PostMapping
  public ResponseEntity<BookResponse> addBook(@RequestBody BookCreateRequest book){
    Book createdBook = bookService.addBook(book);
    return new ResponseEntity<>(toBookResponse(createdBook), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<BookResponse>> getAllBooks(){
   List<BookResponse> books = bookService.getBooks().stream()
     .map(this::toBookResponse)
     .collect(Collectors.toList());
   return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookResponse> getBookById(@PathVariable Long id){
    if(id < 0){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Book book = bookService.findBookById(id).orElseThrow();
    return new ResponseEntity<>(toBookResponse(book), HttpStatus.OK);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<BookResponse> deleteBookById(@PathVariable Long id){
    if(id < 0 ){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Book deletedBook = bookService.deleteBookById(id);
    return new ResponseEntity<>(toBookResponse(deletedBook), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookResponse> updateBook(
    @RequestBody BookUpdateRequest book,
    @PathVariable Long id){
    if(id < 0){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Book updatedBook = bookService.updateBook(book, id);
    return new ResponseEntity<>(toBookResponse(updatedBook), HttpStatus.OK);
  }

  private BookResponse toBookResponse(Book book){
    return new BookResponse(
      book.getId(),
      book.getName(),
      book.getReleaseDate(),
      book.getISBN(),
      book.getAuthors());
  }

}
