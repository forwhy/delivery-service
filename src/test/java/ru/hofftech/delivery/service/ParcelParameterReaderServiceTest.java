package ru.hofftech.delivery.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.exception.InvalidAlgorithmException;
import ru.hofftech.delivery.mapper.LoadingAlgorithmMapper;
import ru.hofftech.delivery.service.validation.InputValidationService;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class ParcelParameterReaderServiceTest {

    @Test
    public void readFilePath_validInput_returnsPath() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("parcels.txt");

        LoadingAlgorithmMapper mapper = Mockito.mock(LoadingAlgorithmMapper.class);

        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);

        assertThat(new ParcelParameterReaderService(scanner, mapper, inputValidationService).readFilePath())
                .isEqualTo("parcels.txt");
    }

    @Test
    public void readFilePath_invalidInput_shouldThrowException() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("");

        LoadingAlgorithmMapper mapper = Mockito.mock(LoadingAlgorithmMapper.class);

        var exceptionMessage = "Input value cannot be empty";
        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);
        doThrow(new IllegalArgumentException(exceptionMessage)).when(inputValidationService).validateInputString("");

        assertThatThrownBy(() -> new ParcelParameterReaderService(scanner, mapper, inputValidationService).readFilePath())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    public void readLoadingAlgorithm_validInput_returnsAlgorithm() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("3");

        LoadingAlgorithmMapper mapper = Mockito.mock(LoadingAlgorithmMapper.class);
        when(mapper.mapIntToLoadingAlgorithm(3)).thenReturn(LoadingAlgorithm.BALANCED);

        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);

        assertThat(new ParcelParameterReaderService(scanner, mapper, inputValidationService).readLoadingAlgorithm())
                .isEqualTo(LoadingAlgorithm.BALANCED);
    }

    @Test
    public void readLoadingAlgorithm_invalidInput_shouldThrowException() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("test");

        LoadingAlgorithmMapper mapper = Mockito.mock(LoadingAlgorithmMapper.class);

        var exceptionMessage = "Integer value expected, but got %s".formatted("test");
        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);
        doThrow(new IllegalArgumentException(exceptionMessage)).when(inputValidationService).validateInputStringConvertibleToInteger("test");

        assertThatThrownBy(() -> new ParcelParameterReaderService(scanner, mapper, inputValidationService).readLoadingAlgorithm())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    public void readLoadingAlgorithm_invalidAlgorithm_shouldThrowException() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("8");

        LoadingAlgorithmMapper mapper = Mockito.mock(LoadingAlgorithmMapper.class);
        doThrow(new InvalidAlgorithmException(8)).when(mapper).mapIntToLoadingAlgorithm(8);

        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);

        assertThatThrownBy(() -> new ParcelParameterReaderService(scanner, mapper, inputValidationService).readLoadingAlgorithm())
                .isInstanceOf(InvalidAlgorithmException.class)
                .hasMessage("Invalid algorithm was chosen: 8");
    }

    @Test
    public void readTrucksCountLimit_validInput_returnsCount() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("3");

        LoadingAlgorithmMapper mapper = Mockito.mock(LoadingAlgorithmMapper.class);

        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);

        assertThat(new ParcelParameterReaderService(scanner, mapper, inputValidationService).readTrucksCountLimit())
                .isEqualTo(3);
    }

    @Test
    public void readTrucksCountLimit_invalidInput_shouldThrowException() {
        Scanner scanner = Mockito.mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("test");

        LoadingAlgorithmMapper mapper = Mockito.mock(LoadingAlgorithmMapper.class);

        var exceptionMessage = "Integer value expected, but got %s".formatted("test");
        InputValidationService inputValidationService = Mockito.mock(InputValidationService.class);
        doThrow(new IllegalArgumentException(exceptionMessage)).when(inputValidationService).validateInputStringConvertibleToInteger("test");

        assertThatThrownBy(() -> new ParcelParameterReaderService(scanner, mapper, inputValidationService).readTrucksCountLimit())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(exceptionMessage);
    }
}