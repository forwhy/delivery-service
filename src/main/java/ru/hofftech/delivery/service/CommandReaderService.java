package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.mapper.CommandMapper;
import ru.hofftech.delivery.service.validation.InputValidationService;

import java.util.Scanner;

@RequiredArgsConstructor
public class CommandReaderService {

    private final Scanner scanner;
    private final CommandMapper commandMapper;
    private final InputValidationService inputValidationService;

    public Command readCommand() {
        System.out.printf(
                "Choose process you want to start: %d - %s, %d - %s, %d - %s%n",
                Command.IMPORT_TRUCKS_EXPORT_PARCELS.getCommandNumber(),
                Command.IMPORT_TRUCKS_EXPORT_PARCELS.name(),
                Command.IMPORT_PARCELS_EXPORT_TRUCKS.getCommandNumber(),
                Command.IMPORT_PARCELS_EXPORT_TRUCKS.name(),
                Command.EXIT.getCommandNumber(),
                Command.EXIT.name());

        var input = scanner.nextLine();
        inputValidationService.validateInputStringConvertibleToInteger(input);
        return commandMapper.mapIntToCommand(Integer.parseInt(input));
    }
}
