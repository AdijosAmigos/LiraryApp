package com.example.LibraryApp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.LibraryApp.model.Book;
import com.example.LibraryApp.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {
  @Mock
  BookRepository bookRepository;

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
    verify(bookRepository).save(createdBook);
    Assertions.assertThat(createdBook).isEqualTo(createdBook);
  }

  @Test
  void shouldFindAllBooks(){
    //given
    List allBooks = new ArrayList();
    allBooks.add(book);
    given(bookRepository.findAll()).willReturn(allBooks);
    //when
    var result = bookRepository.findAll();
    //then
    verify(bookRepository).findAll();
    Assertions.assertThat(result).contains(book);
  }

  @Test
  void shouldFindBookById(){
    List allBooks = new ArrayList();
    allBooks.add(book);
    given(bookRepository.findById(book.getId())).willReturn(Optional.ofNullable(book));
    //when
    var result = bookRepository.findById(book.getId());
    //then
    verify(bookRepository).findById(book.getId());
    Assertions.assertThat(result).contains(book);
  }

  @Test
  void shouldDeleteBook(){
    //given
    bookRepository.save(book);
    //when
    bookRepository.deleteById(book.getId());
    //then
    verify(bookRepository).deleteById(book.getId());
    Assertions.assertThat(bookRepository.findAll()).isEmpty();
  }

}