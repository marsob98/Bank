package org.example.Exception;

public class InvalidPeselException extends RuntimeException {
    public InvalidPeselException(String message) {
        super(message);
    }
}
