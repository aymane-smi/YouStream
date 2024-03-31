package com.example.demo.Configurations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.Exceptions.UnAuthorizedException;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class AdviceConfiguration {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(Exception.class)
    public Map<String, String> global(Exception e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "error test");
        return errorMap;
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleUnAuthorized(UnAuthorizedException e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "unathorized");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public Map<String, String> handleExpireToken(ExpiredJwtException e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "expired");
        return errorMap;
    }
}
