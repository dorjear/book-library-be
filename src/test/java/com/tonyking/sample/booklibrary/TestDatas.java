package com.tonyking.sample.booklibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonyking.sample.booklibrary.model.Book;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestDatas {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static List<Book> BOOKS_SORTED;
    public static List<Book> BOOKS_UNSORTED;

    static {
        try {
            BOOKS_SORTED = Arrays.stream(objectMapper.readValue(TestDatas.class.getResourceAsStream("/books-sorted.json"), Book[].class)).toList();
            BOOKS_UNSORTED = Arrays.stream(objectMapper.readValue(TestDatas.class.getResourceAsStream("/books-unsorted.json"), Book[].class)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
