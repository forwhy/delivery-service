package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class TruckParameterReaderService {

    private final Scanner scanner;

    public String readFilePath() {
        System.out.println("Enter path to a file with trucks:");
        return scanner.nextLine();
    }
}
