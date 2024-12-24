package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.enums.mapper.LoadingAlgorithmMapper;

import java.util.Scanner;

@RequiredArgsConstructor
public class ParcelParameterReaderService {
    private final Scanner scanner;
    private final LoadingAlgorithmMapper loadingAlgorithmMapper;

    public String readFilePath() {
        System.out.println("Enter path to a file with parcels:");
        return scanner.nextLine();
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

        return loadingAlgorithmMapper.mapIntToLoadingAlgorithm(scanner.nextInt());
    }

    public Integer readTrucksCountLimit() {
        System.out.println("Enter trucks count limit:");
        return scanner.nextInt();
    }
}
