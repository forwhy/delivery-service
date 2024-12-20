package ru.hofftech.delivery.exception;

public class InvalidFilePathException extends RuntimeException {
  public InvalidFilePathException(String filePath) {

    super(String.format("Invalid file path: %s", filePath));
  }
}
