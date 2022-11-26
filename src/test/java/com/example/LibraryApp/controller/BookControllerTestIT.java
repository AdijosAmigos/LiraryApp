package com.example.LibraryApp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.dto.bookdto.BookUpdateRequest;
import com.example.LibraryApp.repository.BookRepository;
import com.example.LibraryApp.repository.UserRepository;
import com.example.LibraryApp.service.BookService;
import com.example.LibraryApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTestIT {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  ApplicationContext context;

  Book book;

  private String domain = "http://localhost:";

  @BeforeEach
  void addBook(){
    book = new Book(1L, "testName", LocalDate.now(), "testISBN", "testAuthor");
  }

  @Test
  void shouldAddBook(){
    var result = restTemplate.postForEntity(domain + port + "/books", book, Book.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(201);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(Book.class);
    assertThat(result.getBody()).isEqualTo(book);
  }

  @Test
  void shouldFindBookById(){
    Book book2 = new Book(2L, "testName2", LocalDate.now(), "testISBN2", "testAuthor2");
    bookRepository.save(book);
    bookRepository.save(book2);
    var result = restTemplate.getForEntity(domain + port + "/books/1", Book.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(Book.class);
    assertThat(result.getBody()).isEqualTo(book);
  }

  @Test
  void shouldFindAllBooks(){
    bookRepository.save(book);
    var result = restTemplate.getForEntity(domain + port + "/books", Book[].class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody()).isInstanceOf(Book[].class);
    assertThat(result.getBody()).containsExactlyInAnyOrder(book);
  }

  @Test
  void shouldDeleteBook(){
    bookRepository.save(book);
    restTemplate.delete(domain + port + "/books/delete/1");

    assertThat(bookRepository.findAll()).isEmpty();
  }

  @Test
  void shouldUpdateBook(){
    bookRepository.save(book);

    BookUpdateRequest request = new BookUpdateRequest("updatedBook", LocalDate.now(), "updatedISBN");
    HttpEntity<BookUpdateRequest> httpEntity = new HttpEntity<>(request);
    var result = restTemplate.exchange(
      domain + port + "/books/1", HttpMethod.PUT, httpEntity, Book.class);

    assertThat(result.getStatusCodeValue()).isEqualTo(200);
    assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.hasBody()).isTrue();
    assertThat(result.getBody().getId()).isEqualTo(book.getId());
    assertThat(result.getBody().getName()).isEqualTo(httpEntity.getBody().getName());
    assertThat(result.getBody().getReleaseDate()).isEqualTo(httpEntity.getBody().getReleaseDate());
    assertThat(result.getBody().getISBN()).isEqualTo(httpEntity.getBody().getISBN());

  }

}