package ru.hofftech.deliveryservice.telegramclient.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.service.ParcelsLoaderClient;
import ru.hofftech.deliveryservice.telegramclient.service.command.UnloadCommandParser;

@Component
@RequiredArgsConstructor
public class UnloadTrucksCommand implements Command {

    private final UnloadCommandParser unloadCommandParser;
    private final ParcelsLoaderClient parcelsLoaderClient;

    @Override
    public String execute(String commandText) {
        return parcelsLoaderClient.unload(unloadCommandParser.parse(commandText)).formatAsReport();
    }
}
