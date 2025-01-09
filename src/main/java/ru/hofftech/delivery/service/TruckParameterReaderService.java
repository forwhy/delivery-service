package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.service.validation.InputValidationService;

import java.util.Scanner;

@RequiredArgsConstructor
public class TruckParameterReaderService {

    private final Scanner scanner;
    private final InputValidationService inputValidationService;

    public String readFilePath() {
        System.out.println("Enter path to a file with trucks:");

        var path = scanner.nextLine();
        inputValidationService.validateInputString(path);
        return path;
    }
}
