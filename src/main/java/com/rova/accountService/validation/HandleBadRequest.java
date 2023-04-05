package com.rova.accountService.validation;

import com.rova.accountService.dto.RevoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class HandleBadRequest {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RevoResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(new RevoResponse("500", "Something went wrong", errors, null), HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<RevoResponse> genericException(Exception exception) {
//        Map<String, String> errors = new HashMap<>();
//        return new ResponseEntity<>(new RevoResponse("500", "Something went wrong", errors, null), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}