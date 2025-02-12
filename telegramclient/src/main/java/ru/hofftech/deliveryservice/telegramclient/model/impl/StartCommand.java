package ru.hofftech.deliveryservice.telegramclient.model.impl;

import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;

@Component
public class StartCommand implements Command {

    @Override
    public String execute(String commandText) {
        return "Привет! Для получения справки используйте команду /help.";
    }
}
