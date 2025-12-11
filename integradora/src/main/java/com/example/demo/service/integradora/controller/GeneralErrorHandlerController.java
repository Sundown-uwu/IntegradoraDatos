package com.example.demo.service.integradora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.service.integradora.dto.ErrorResponse;

@RestControllerAdvice
public class GeneralErrorHandlerController {

    @ExceptionHandler(Exception.class)

    public ResponseEntity<?> generalErrorHandler(Exception e){
        ErrorResponse error = new ErrorResponse();
        error.setError("Error general de spring :c");
        error.setDetail(e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

}
