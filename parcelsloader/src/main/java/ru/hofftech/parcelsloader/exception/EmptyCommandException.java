package ru.hofftech.parcelsloader.exception;

public class EmptyCommandException extends RuntimeException {

    public EmptyCommandException() {
      super("Передана пустая команда");
    }
}
