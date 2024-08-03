package com.example.nodebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DiaryAlreadyExistsException extends RuntimeException {
    public DiaryAlreadyExistsException(String message) {
        super(message);
    }
}