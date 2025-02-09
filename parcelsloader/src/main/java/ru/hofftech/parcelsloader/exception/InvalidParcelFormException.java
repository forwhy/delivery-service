package ru.hofftech.parcelsloader.exception;

public class InvalidParcelFormException extends RuntimeException {

    public InvalidParcelFormException(String form) {
        super(String.format("Некорректная форма посылки, сегменты не должны касаться только по диагонали:%n%s", form));
    }
}
