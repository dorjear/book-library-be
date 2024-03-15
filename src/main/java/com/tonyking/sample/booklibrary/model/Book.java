package com.tonyking.sample.booklibrary.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String author;

    public Book(String name, String author, BookStatus status) {
        this.name = name;
        this.author = author;
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    private BookStatus status;

}
