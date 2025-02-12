package ru.hofftech.deliveryservice.telegramclient.controller;

import ru.hofftech.deliveryservice.telegramclient.service.TelegramService;

public class TelegramController {

    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }
}
