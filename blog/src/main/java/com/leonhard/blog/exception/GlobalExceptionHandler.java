package com.leonhard.blog.exception;

import com.leonhard.blog.dtos.ExceptionDto;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .details(webRequest.getDescription(false))
                .message(e.getMessage())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BlogAPIException.class)
    public ResponseEntity<ExceptionDto> handleBlogAPIException(BlogAPIException e, WebRequest webRequest) {

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .details(webRequest.getDescription(false))
                .message(e.getMessage())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDto> handleGlobalException(Exception e, WebRequest webRequest) {

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .details(webRequest.getDescription(false))
                .message(e.getMessage())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            errors.put(fieldName, message);
        });
        System.out.println("errors = " + errors);
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

}
