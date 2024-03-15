package com.tonyking.sample.booklibrary.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookStatus {
    BORROWED("Borrowed"),
    AVAILABLE("Available");

    private final String value;

    BookStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static BookStatus fromValue(String value) {
        for (BookStatus status : BookStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid book status value: " + value);
    }
}