package ru.romanov.cinema.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UserCreationException extends RuntimeException {
    public UserCreationException(String message, DataIntegrityViolationException ex) {
        super(message);
    }
}
