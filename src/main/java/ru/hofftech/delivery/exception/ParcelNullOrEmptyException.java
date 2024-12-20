package ru.hofftech.delivery.exception;

public class ParcelNullOrEmptyException extends RuntimeException{

    public ParcelNullOrEmptyException(){
        super("Parcel string cannot be null or empty");
    }
}
