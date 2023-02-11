package ru.kata.spring.boot_security.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsersExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<UserExceptionInfo> handleException (UserNotCreatedException e) {
        UserExceptionInfo exceptionInfo = new UserExceptionInfo("User saving error");
        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionInfo> handleException (UserNotFoundException e) {
        UserExceptionInfo exceptionInfo = new UserExceptionInfo("No such user was found");
        return new ResponseEntity<>(exceptionInfo, HttpStatus.NOT_FOUND);
    }
}
