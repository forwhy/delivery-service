package ru.hofftech.delivery.enums.mapper;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.exception.InvalidCommandException;
import ru.hofftech.delivery.service.validation.CommandValidationService;

@RequiredArgsConstructor
public class CommandMapper {
    private final CommandValidationService commandValidationService;

    public Command mapIntToCommand(Integer commandNumber) {
        commandValidationService.validateCommandNumber(commandNumber);
        return switch (commandNumber) {
            case 1 -> Command.IMPORT_TRUCKS_EXPORT_PARCELS;
            case 2 -> Command.IMPORT_PARCELS_EXPORT_TRUCKS;
            default -> throw new InvalidCommandException(commandNumber);
        };
    }
}
