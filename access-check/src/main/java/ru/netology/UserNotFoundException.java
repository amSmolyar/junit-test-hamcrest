package ru.netology;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String exceptionText) {
        super(exceptionText);
    }
}
