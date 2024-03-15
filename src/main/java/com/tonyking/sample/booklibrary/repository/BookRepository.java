package com.tonyking.sample.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tonyking.sample.booklibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
