package ru.hofftech.telegramclient.controller;

import ru.hofftech.telegramclient.service.TelegramService;

public class TelegramController {

    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
        this.telegramService.init();
    }
}
