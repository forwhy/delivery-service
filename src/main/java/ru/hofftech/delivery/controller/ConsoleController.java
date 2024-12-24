package ru.hofftech.delivery.controller;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.factory.ProcessingControllerFactory;
import ru.hofftech.delivery.service.CommandReaderService;

@RequiredArgsConstructor
public class ConsoleController {
    private final CommandReaderService commandReaderService;
    private final ProcessingControllerFactory processingControllerFactory;

    public void listen() {
        Command command = commandReaderService.readCommand();
        processingControllerFactory.initProcessingController(command).start();
    }
}
