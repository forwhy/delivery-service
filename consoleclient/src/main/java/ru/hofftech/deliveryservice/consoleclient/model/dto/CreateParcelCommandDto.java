package ru.hofftech.deliveryservice.consoleclient.model.dto;

public record CreateParcelCommandDto(String name,
                                     String form,
                                     Character symbol) {
}
