package com.github.receipes;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final ErrorResponse error;

    BadRequestException(String message, ErrorResponse error) {
        super(message);
        this.error = error;
    }
}
