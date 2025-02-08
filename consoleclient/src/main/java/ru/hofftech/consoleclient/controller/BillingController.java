package ru.hofftech.consoleclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.consoleclient.service.ParcelsLoaderClient;

@ShellComponent
@RequiredArgsConstructor
public class BillingController {

    private final ParcelsLoaderClient parcelsLoaderClient;

    @ShellMethod(key = "/billing", value = "Получить квитанции по имени пользователя")
    public String findByUser(@ShellOption(value = "u") String user) {
        return parcelsLoaderClient.findByUser(user);
    }
}
