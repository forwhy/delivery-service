package ru.hofftech.consoleclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.consoleclient.model.record.CreateParcelCommand;
import ru.hofftech.consoleclient.service.ParcelsLoaderClient;

@ShellComponent
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelsLoaderClient parcelsLoaderClient;

    @ShellMethod(key = "/create", value = "Создание новой посылки.")
    public String create(
            @ShellOption(value = "name") String name,
            @ShellOption(value = "form") String form,
            @ShellOption(value = "symbol") Character symbol) {
        return parcelsLoaderClient.createParcel(new CreateParcelCommand(name, form, symbol));
    }

    @ShellMethod(key = "/find", value = "Найти посылку по имени.")
    public String find(
            @ShellOption String name) {
        return parcelsLoaderClient.findParcel(name);
    }

    @ShellMethod(key = "/edit", value = "Редактировать посылку.")
    public String edit(
            @ShellOption String id,
            @ShellOption(value = "name") String name,
            @ShellOption(value = "form") String form,
            @ShellOption(value = "symbol") Character symbol) {
        return parcelsLoaderClient.updateParcel(id, new CreateParcelCommand(name, form, symbol));
    }

    @ShellMethod(key = "/delete", value = "Удалить посылку по названию.")
    public String delete(
            @ShellOption String name) {
        return parcelsLoaderClient.deleteParcel(name);
    }
}
