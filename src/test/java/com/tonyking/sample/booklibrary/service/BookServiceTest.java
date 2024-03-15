package com.tonyking.sample.booklibrary.service;

import com.tonyking.sample.booklibrary.TestDatas;
import com.tonyking.sample.booklibrary.model.Book;
import com.tonyking.sample.booklibrary.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private static final List<Book> sortedBooks = TestDatas.BOOKS_SORTED;

    @Test
    void getAllBooksReturnsSortedBooks() {

        List<Book> unsortedBooks = new ArrayList<>(TestDatas.BOOKS_UNSORTED);

        when(bookRepository.findAll()).thenReturn(unsortedBooks);

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(sortedBooks.size(), books.size());
        assertEquals(sortedBooks, books);

        verify(bookRepository).findAll();
    }

    @Test
    void getBookByIdFound() {
        Book book = TestDatas.BOOKS_UNSORTED.get(0);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals(book, foundBook.get());
    }

    @Test
    void createBookSavesAndReturnsBook() {
        Book book = TestDatas.BOOKS_SORTED.get(0);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        assertNotNull(createdBook);
        assertEquals(book.getName(), createdBook.getName());

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBookUpdatesAndReturnsBook() {
        Book originalBook = TestDatas.BOOKS_SORTED.get(1);
        Book updatedBook = new Book(originalBook.getName()+"Suffix", originalBook.getAuthor()+"Suffix", originalBook.getStatus());
        updatedBook.setId(originalBook.getId());
        when(bookRepository.findById(4L)).thenReturn(Optional.of(originalBook));
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        Book result = bookService.updateBook(updatedBook);
        assertNotNull(result);
        assertEquals(updatedBook, result);
        verify(bookRepository).findById(4L);
        verify(bookRepository).save(updatedBook);
    }

    @Test
    void deleteBookDeletesById() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    void updateBookThrowsExceptionWhenNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.updateBook(new Book());
        });

        assertTrue(exception.getMessage().contains("Book id not found"));
    }
}