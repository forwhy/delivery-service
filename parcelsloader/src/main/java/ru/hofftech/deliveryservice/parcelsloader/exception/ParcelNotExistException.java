package ru.hofftech.deliveryservice.parcelsloader.exception;

public class ParcelNotExistException extends RuntimeException {
  public ParcelNotExistException(String message) {
    super(message);
  }
}
