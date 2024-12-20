package ru.hofftech.delivery.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvalidParcelException extends RuntimeException {
    public InvalidParcelException(String message) {
        super(message);
    }
}
