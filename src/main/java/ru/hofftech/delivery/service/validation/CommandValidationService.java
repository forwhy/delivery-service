package ru.hofftech.delivery.service.validation;

import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.exception.InvalidCommandException;

import java.util.Arrays;

public class CommandValidationService {
    public void validateCommandNumber(Integer commandNumber) {
        if (Arrays.stream(Command.values()).noneMatch(a -> a.getCommandNumber().equals(commandNumber))) {
            throw new InvalidCommandException(commandNumber);
        }
    }
}
