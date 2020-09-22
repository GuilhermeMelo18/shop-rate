package com.project.shoprate.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class SystemExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException .class)
    public ResponseEntity<Object> exception(MethodArgumentNotValidException exception) {

        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        RequiredFieldsResponse response = new RequiredFieldsResponse();
        response.setFields(errors.stream().map(FieldError::getField).collect(Collectors.toList()));
        return ResponseEntity.badRequest().body(response);
    }
}
