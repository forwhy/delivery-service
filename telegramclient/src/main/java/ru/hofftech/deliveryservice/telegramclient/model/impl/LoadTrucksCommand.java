package ru.hofftech.deliveryservice.telegramclient.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.service.ParcelsLoaderClient;
import ru.hofftech.deliveryservice.telegramclient.service.command.LoadCommandParser;

@Component
@RequiredArgsConstructor
public class LoadTrucksCommand implements Command {

    private final LoadCommandParser loadCommandParser;
    private final ParcelsLoaderClient parcelsLoaderClient;

    @Override
    public String execute(String commandText) {
        return parcelsLoaderClient.load(loadCommandParser.parse(commandText)).formatAsReport();
    }
}
