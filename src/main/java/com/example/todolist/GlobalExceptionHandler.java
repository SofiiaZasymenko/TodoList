package com.example.todolist;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoteException.class)
    public ResponseEntity<Map<String, List<String>>> noteException(Exception ex) {
        return new ResponseEntity<>(Map.of("errors", List.of(ex.getMessage())), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}