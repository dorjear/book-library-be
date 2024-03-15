package com.tonyking.sample.booklibrary.service;

import com.tonyking.sample.booklibrary.model.Book;
import com.tonyking.sample.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        if (!allBooks.isEmpty()) {
            allBooks.sort(Comparator.comparing(book -> book.getName().toLowerCase()));
        }
        return allBooks;
    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(new Book(book.getName(), book.getAuthor(), book.getStatus()));
    }

    public Book updateBook(@RequestBody Book book) {
        Optional<Book> bookData = bookRepository.findById(book.getId());
        if (bookData.isPresent()) {
            Book bookFromDb = bookData.get();
            bookFromDb.setName(book.getName());
            bookFromDb.setAuthor(book.getAuthor());
            bookFromDb.setStatus(book.getStatus());
            return bookRepository.save(bookFromDb);
        } else {
            throw new IllegalArgumentException("Book id not found");
        }
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }
}
