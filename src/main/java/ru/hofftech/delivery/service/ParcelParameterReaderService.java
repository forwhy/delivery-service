package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.mapper.LoadingAlgorithmMapper;
import ru.hofftech.delivery.service.validation.InputValidationService;

import java.util.Scanner;

@RequiredArgsConstructor
public class ParcelParameterReaderService {
    private final Scanner scanner;
    private final LoadingAlgorithmMapper loadingAlgorithmMapper;
    private final InputValidationService inputValidationService;

    public String readFilePath() {
        System.out.println("Enter path to a file with parcels:");
        var input = scanner.nextLine();
        inputValidationService.validateInputString(input);
        return input;
    }

    public LoadingAlgorithm readLoadingAlgorithm() {
        System.out.printf(
                "Enter placement strategy to use: %d - %s, %d - %s, %d - %s%n",
                LoadingAlgorithm.ONE_TRUCK_PER_PARCEL.getAlgorithmNumber(),
                LoadingAlgorithm.ONE_TRUCK_PER_PARCEL.name(),
                LoadingAlgorithm.WIDE_FIRST.getAlgorithmNumber(),
                LoadingAlgorithm.WIDE_FIRST.name(),
                LoadingAlgorithm.BALANCED.getAlgorithmNumber(),
                LoadingAlgorithm.BALANCED.name());

        var input = scanner.nextLine();
        inputValidationService.validateInputStringConvertibleToInteger(input);
        return loadingAlgorithmMapper.mapIntToLoadingAlgorithm(Integer.parseInt(input));
    }

    public Integer readTrucksCountLimit() {
        System.out.println("Enter trucks count limit:");
        var input = scanner.nextLine();
        inputValidationService.validateInputStringConvertibleToInteger(input);
        return Integer.parseInt(input);
    }
}
