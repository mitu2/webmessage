package com.brageast.project.webmessage.exception;

import com.brageast.project.webmessage.entity.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.brageast.project.webmessage.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler({UserExistedException.class, UserNotFoundException.class, UserLoginFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(Exception err/*, WebRequest webRequest*/) {
        return ResponseEntity
                .badRequest()
                .body(ResponseMessage
                        .of(HttpStatus.BAD_REQUEST)
                        .data(err.getMessage())
                        .build()
                );
    }

}
