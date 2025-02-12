package ru.hofftech.deliveryservice.telegramclient.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.model.dto.response.ParcelDto;
import ru.hofftech.deliveryservice.telegramclient.service.ParcelsLoaderClient;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindAllParcelCommand implements Command {

    private final ParcelsLoaderClient parcelsLoaderClient;

    @Override
    public String execute(String commandText) {
        return parcelsLoaderClient.findAll().stream()
                .map(ParcelDto::formatAsText)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
