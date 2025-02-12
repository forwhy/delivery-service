package ru.hofftech.deliveryservice.telegramclient.model.dto;

public record CreateParcelCommandDto(String name,
                                     String form,
                                     Character symbol) {
}
