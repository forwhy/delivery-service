package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.enums.mapper.CommandMapper;

import java.util.Scanner;

@RequiredArgsConstructor
public class CommandReaderService {
    private final Scanner scanner;
    private final CommandMapper commandMapper;

    public Command readCommand() {
        System.out.printf(
                "Choose process you want to start: %d - %s, %d - %s\n%n",
                Command.IMPORT_TRUCKS_EXPORT_PARCELS.getCommandNumber(),
                Command.IMPORT_TRUCKS_EXPORT_PARCELS.name(),
                Command.IMPORT_PARCELS_EXPORT_TRUCKS.getCommandNumber(),
                Command.IMPORT_PARCELS_EXPORT_TRUCKS.name());

        return commandMapper.mapIntToCommand(scanner.nextInt());
    }
}
