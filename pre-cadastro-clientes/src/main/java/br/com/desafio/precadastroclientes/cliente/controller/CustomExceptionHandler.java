package br.com.desafio.precadastroclientes.cliente.controller;

import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid() {
        return new ResponseEntity<>("Erro na validação dos dados", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation() {
        return new ResponseEntity<>("Erro na validação dos dados", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<Object> handleIntegrationException(IntegrationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}