package ru.hofftech.delivery.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hofftech.delivery.service.validation.InputValidationService;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class TruckParameterReaderServiceTest {

    @Test
    public void readFilePath_validInput_returnsPath() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("text.txt");

        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);

        assertThat(new TruckParameterReaderService(scanner, inputValidationService).readFilePath())
                .isEqualTo("text.txt");
    }

    @Test
    public void readFilePath_invalidInput_shouldThrowException() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("");

        var exceptionMessage = "Input value cannot be empty";
        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);
        doThrow(new IllegalArgumentException(exceptionMessage)).when(inputValidationService).validateInputString("");

        assertThatThrownBy(() -> new TruckParameterReaderService(scanner, inputValidationService).readFilePath())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(exceptionMessage);
    }
}