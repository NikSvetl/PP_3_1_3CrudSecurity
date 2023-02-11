package ru.kata.spring.boot_security.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsersExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<UserExceptionInfo> handleException (UserNotCreatedException e) {
        UserExceptionInfo exceptionInfo = new UserExceptionInfo();
        exceptionInfo.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionInfo> handleException (UserNotFoundException e) {
        UserExceptionInfo exceptionInfo = new UserExceptionInfo();
        exceptionInfo.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionInfo, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<UserExceptionInfo> handleException (Exception e) {
        UserExceptionInfo exceptionInfo = new UserExceptionInfo();
        exceptionInfo.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }
}
