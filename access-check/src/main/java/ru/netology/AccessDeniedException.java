package ru.netology;

public class AccessDeniedException extends Exception {
    public AccessDeniedException(String exceptionText) {
        super(exceptionText);
    }
}
