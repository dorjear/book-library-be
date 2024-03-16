package com.tonyking.sample.booklibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonyking.sample.booklibrary.SecurityConfig;
import com.tonyking.sample.booklibrary.TestDatas;
import com.tonyking.sample.booklibrary.model.Book;
import com.tonyking.sample.booklibrary.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
@Import({GlobalExceptionHandler.class, })
public class BookControllerIntegrationTest {

    @MockBean
    private SecurityConfig securityConfig;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllBooksTest() throws Exception {

        when(bookService.getAllBooks()).thenReturn(TestDatas.BOOKS_SORTED);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TestDatas.BOOKS_SORTED)));

        verify(bookService).getAllBooks();
    }

    @Test
    void getAllBooksNotFoundTest() throws Exception {

        when(bookService.getAllBooks()).thenReturn(List.of());

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isNoContent());

        verify(bookService).getAllBooks();
    }

    @Test
    void getBookByIdTest() throws Exception {

        when(bookService.getBookById(1L)).thenReturn(Optional.of(TestDatas.BOOKS_SORTED.get(0)));

        mockMvc.perform(get("/api/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TestDatas.BOOKS_SORTED.get(0))));

        verify(bookService).getBookById(1L);
    }

    @Test
    void getBookByIdNotFoundTest() throws Exception {

        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/book/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(bookService).getBookById(1L);
    }

    @Test
    void createBookTest() throws Exception {

        when(bookService.createBook(any(Book.class))).thenReturn(TestDatas.BOOKS_SORTED.get(0));

        mockMvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestDatas.BOOKS_SORTED.get(0))))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(TestDatas.BOOKS_SORTED.get(0))));

        verify(bookService).createBook(any(Book.class));
    }

    @Test
    void createBookRuntimeExceptionTest() throws Exception {

        when(bookService.createBook(any(Book.class))).thenThrow(new RuntimeException("RuntimeException"));

        mockMvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestDatas.BOOKS_SORTED.get(0))))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"error\":\"RuntimeException\"}"));

        verify(bookService).createBook(any(Book.class));
    }

    @Test
    void updateBookTest() throws Exception {

        when(bookService.updateBook(any(Book.class))).thenReturn(TestDatas.BOOKS_SORTED.get(0));

        mockMvc.perform(put("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestDatas.BOOKS_SORTED.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TestDatas.BOOKS_SORTED.get(0))));

        verify(bookService).updateBook(any(Book.class));
    }

    @Test
    void updateBookNotFoundTest() throws Exception {

        when(bookService.updateBook(any(Book.class))).thenThrow(new IllegalArgumentException("Book not found"));

        mockMvc.perform(put("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestDatas.BOOKS_SORTED.get(0))))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Book not found\"}"));


        verify(bookService).updateBook(any(Book.class));
    }

    @Test
    void deleteBookTest() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/book/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(bookService).deleteBook(1L);
    }
}
