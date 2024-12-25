package ru.hofftech.delivery.controller;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.service.CommandReaderService;
import ru.hofftech.delivery.service.PackingService;
import ru.hofftech.delivery.service.UnpackingService;

@RequiredArgsConstructor
public class ConsoleController {
    private final CommandReaderService commandReaderService;
    private final PackingService packingService;
    private final UnpackingService unpackingService;

    public void listen() {
        var isWorking = true;

        while (isWorking) {
            Command command = commandReaderService.readCommand();

            switch (command) {
                case IMPORT_TRUCKS_EXPORT_PARCELS -> unpackingService.start();
                case IMPORT_PARCELS_EXPORT_TRUCKS -> packingService.start();
                case EXIT -> isWorking = false;
            }
        }
    }
}
