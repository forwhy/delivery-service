package ru.hofftech.parcelsloader.exception;

public class InvalidFilePathException extends RuntimeException {

  public InvalidFilePathException(String filePath) {
    super(String.format("Некорректный путь к файлу: %s", filePath));
  }
}
