package ru.hofftech.delivery.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.exception.InvalidCommandException;
import ru.hofftech.delivery.mapper.CommandMapper;
import ru.hofftech.delivery.service.validation.InputValidationService;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class CommandReaderServiceTest {

    @Test
    public void readCommand_validInput_shouldReturnCommand() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("1");

        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);

        CommandMapper commandMapper = Mockito.mock(CommandMapper.class);
        when(commandMapper.mapIntToCommand(1)).thenReturn(Command.IMPORT_TRUCKS_EXPORT_PARCELS);

        var command = new CommandReaderService(scanner, commandMapper, inputValidationService)
                .readCommand();

        assertThat(command).isInstanceOf(Command.class);
        assertThat(command).isEqualTo(Command.IMPORT_TRUCKS_EXPORT_PARCELS);
    }

    @Test
    public void readCommand_notExistingCommandInput_shouldThrowException() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("12");

        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);

        CommandMapper commandMapper = Mockito.mock(CommandMapper.class);
        when(commandMapper.mapIntToCommand(12)).thenThrow(new InvalidCommandException(12));

        assertThatThrownBy(() -> new CommandReaderService(scanner, commandMapper, inputValidationService).readCommand())
                .isInstanceOf(InvalidCommandException.class);
    }

    @Test
    public void readCommand_invalidIntegerInput_shouldThrowException() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("krya");

        var exceptionMessage = "Integer value expected, but got %s".formatted("krya");
        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);
        doThrow(new IllegalArgumentException(exceptionMessage)).when(inputValidationService).validateInputStringConvertibleToInteger("krya");

        CommandMapper commandMapper = Mockito.mock(CommandMapper.class);

        assertThatThrownBy(() -> new CommandReaderService(scanner, commandMapper, inputValidationService).readCommand())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    public void readCommand_emptyInput_shouldThrowException() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("");

        var exceptionMessage = "Input value cannot be empty";
        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);
        doThrow(new IllegalArgumentException(exceptionMessage)).when(inputValidationService).validateInputStringConvertibleToInteger("");

        CommandMapper commandMapper = Mockito.mock(CommandMapper.class);

        assertThatThrownBy(() -> new CommandReaderService(scanner, commandMapper, inputValidationService).readCommand())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(exceptionMessage);
    }
}