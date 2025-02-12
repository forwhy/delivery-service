package ru.hofftech.deliveryservice.telegramclient.model.record;

public record EditParcelCommand(String id,
                                CreateParcelCommand createParcelCommand) {
}
