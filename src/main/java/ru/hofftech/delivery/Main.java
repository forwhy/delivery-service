package ru.hofftech.delivery;

import ru.hofftech.delivery.controller.ConsoleController;
import ru.hofftech.delivery.enums.mapper.CommandMapper;
import ru.hofftech.delivery.factory.ProcessingControllerFactory;
import ru.hofftech.delivery.service.CommandReaderService;
import ru.hofftech.delivery.service.validation.CommandValidationService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var consoleController = new ConsoleController(
                new CommandReaderService(
                        new Scanner(System.in),
                        new CommandMapper(new CommandValidationService())),
                new ProcessingControllerFactory());

        consoleController.listen();
    }
}