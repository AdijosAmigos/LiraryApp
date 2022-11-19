package com.example.LibraryApp.service;

import java.time.LocalDate;
import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.model.dto.bookdto.BookCreateRequest;
import com.example.LibraryApp.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {
  @Mock
  BookRepository bookRepository;

  @InjectMocks
  BookService bookService;

  Book book;

  @BeforeEach
  void createBook(){
    book =  new Book(1L, "testBook", LocalDate.now(), "testISBN", "testAuthor");
  }

  @Test
  void shouldAddBook(){
    //given
    given(bookRepository.save(book)).willReturn(book);
    //when
    Book createdBook = bookRepository.save(book);
    //then
    Assertions.assertThat(createdBook)
      .isEqualTo(new Book(1L, "testBook", LocalDate.now(), "testISBN", "testAuthor"));


  }

}