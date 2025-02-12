package ru.hofftech.deliveryservice.consoleclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.deliveryservice.consoleclient.model.dto.CreateParcelCommandDto;
import ru.hofftech.deliveryservice.consoleclient.model.dto.ParcelDto;
import ru.hofftech.deliveryservice.consoleclient.service.ParcelsLoaderClient;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelsLoaderClient parcelsLoaderClient;

    @ShellMethod(key = "/create", value = "Создание новой посылки.")
    public String create(
            @ShellOption(value = "name") String name,
            @ShellOption(value = "form") String form,
            @ShellOption(value = "symbol") Character symbol) {
        return parcelsLoaderClient.createParcel(new CreateParcelCommandDto(name, form, symbol)).formatAsText();
    }

    @ShellMethod(key = "/find", value = "Найти посылку по имени.")
    public String find(
            @ShellOption String name) {
        return parcelsLoaderClient.findParcel(name).formatAsText();
    }

    @ShellMethod(key = "/find-all", value = "Получить все посылки.")
    public String find() {
        List<ParcelDto> parcels = parcelsLoaderClient.findAll();
        return parcels.stream()
                .map(ParcelDto::formatAsText)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(key = "/edit", value = "Редактировать посылку.")
    public String edit(
            @ShellOption String id,
            @ShellOption(value = "name") String name,
            @ShellOption(value = "form") String form,
            @ShellOption(value = "symbol") Character symbol) {
        return parcelsLoaderClient.updateParcel(id, new CreateParcelCommandDto(name, form, symbol)).formatAsText();
    }

    @ShellMethod(key = "/delete", value = "Удалить посылку по названию.")
    public String delete(
            @ShellOption String name) {
        parcelsLoaderClient.deleteParcel(name);
        return "Посылка успешно удалена";
    }
}
