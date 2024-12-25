package ru.hofftech.delivery.mapper;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.exception.InvalidCommandException;

@RequiredArgsConstructor
public class CommandMapper {

    public Command mapIntToCommand(Integer commandNumber) {
        return switch (commandNumber) {
            case 1 -> Command.IMPORT_TRUCKS_EXPORT_PARCELS;
            case 2 -> Command.IMPORT_PARCELS_EXPORT_TRUCKS;
            default -> throw new InvalidCommandException(commandNumber);
        };
    }
}
