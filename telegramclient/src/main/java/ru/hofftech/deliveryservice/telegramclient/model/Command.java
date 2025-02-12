package ru.hofftech.deliveryservice.telegramclient.model;

public interface Command {
    String execute(String commandText);
}
