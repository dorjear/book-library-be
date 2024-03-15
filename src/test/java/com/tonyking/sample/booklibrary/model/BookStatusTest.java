package com.tonyking.sample.booklibrary.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookStatusTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testEnumValues() {
        BookStatus[] statuses = BookStatus.values();
        assertEquals(2, statuses.length);
        assertEquals(BookStatus.BORROWED, statuses[0]);
        assertEquals(BookStatus.AVAILABLE, statuses[1]);
    }

    @Test
    public void testGetValue() {
        assertEquals("Borrowed", BookStatus.BORROWED.getValue());
        assertEquals("Available", BookStatus.AVAILABLE.getValue());
    }

    @Test
    public void testFromValue() {
        assertEquals(BookStatus.BORROWED, BookStatus.fromValue("Borrowed"));
        assertEquals(BookStatus.AVAILABLE, BookStatus.fromValue("Available"));
        assertEquals(BookStatus.BORROWED, BookStatus.fromValue("bOrRoWeD")); // case-insensitive
    }

    @Test
    public void testFromValueInvalid() {
        assertThrows(IllegalArgumentException.class, () -> BookStatus.fromValue("InvalidStatus"));
    }

    @Test
    public void testEnumToString() {
        assertEquals("BORROWED", BookStatus.BORROWED.toString());
        assertEquals("AVAILABLE", BookStatus.AVAILABLE.toString());
    }

    @Test
    public void testToJson() throws JsonProcessingException {
        String borrowedJson = objectMapper.writeValueAsString(BookStatus.BORROWED);
        assertEquals("\"Borrowed\"", borrowedJson);

        String availableJson = objectMapper.writeValueAsString(BookStatus.AVAILABLE);
        assertEquals("\"Available\"", availableJson);
    }

    @Test
    public void testFromJson() throws JsonProcessingException {
        BookStatus borrowed = objectMapper.readValue("\"Borrowed\"", BookStatus.class);
        assertEquals(BookStatus.BORROWED, borrowed);

        BookStatus available = objectMapper.readValue("\"Available\"", BookStatus.class);
        assertEquals(BookStatus.AVAILABLE, available);
    }

    @Test
    public void testFromJsonInvalid() {
        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue("\"InvalidStatus\"", BookStatus.class));
    }

    @Test
    public void testComparing() {
        List<String> list = new ArrayList<>(List.of("available", "bor", "Cancelled"));
        list.sort(String::compareToIgnoreCase);
        System.out.println(list);
    }
}