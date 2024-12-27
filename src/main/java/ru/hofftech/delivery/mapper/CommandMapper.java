package ru.hofftech.delivery.mapper;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.exception.InvalidCommandException;

import java.util.Map;

@RequiredArgsConstructor
public class CommandMapper {

    private static final Map<Integer, Command> COMMAND_MAP = Map.of(
            1, Command.IMPORT_TRUCKS_EXPORT_PARCELS,
            2, Command.IMPORT_PARCELS_EXPORT_TRUCKS,
            3, Command.EXIT
    );

    public Command mapIntToCommand(Integer commandNumber) {
        if (!COMMAND_MAP.containsKey(commandNumber)) {
            throw new InvalidCommandException(commandNumber);
        }

        return COMMAND_MAP.get(commandNumber);
    }
}
