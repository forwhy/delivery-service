package ru.hofftech.telegramclient.model.record;

public record EditParcelCommand(String id,
                                CreateParcelCommand createParcelCommand) {
}
