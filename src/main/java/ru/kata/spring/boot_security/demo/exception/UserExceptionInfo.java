package ru.kata.spring.boot_security.demo.exception;

public class UserExceptionInfo {

    private String message;

    public UserExceptionInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
