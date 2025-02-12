package ru.hofftech.deliveryservice.telegramclient.model.record;

public record CreateParcelCommand (String name,
                                   String form,
                                   Character symbol) {
}
