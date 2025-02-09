package ru.hofftech.parcelsloader.exception;

public class ParcelNullOrEmptyException extends RuntimeException{

    public ParcelNullOrEmptyException(){
        super("Посылка не может быть пустой строкой");
    }
}
