package ru.hofftech.deliveryservice.telegramclient.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.service.ParcelsLoaderClient;
import ru.hofftech.deliveryservice.telegramclient.service.command.FindCommandParser;

@Component
@RequiredArgsConstructor
public class FindParcelCommand implements Command {

    private final FindCommandParser findCommandParser;
    private final ParcelsLoaderClient parcelsLoaderClient;

    @Override
    public String execute(String commandText) {
        return parcelsLoaderClient.findParcel(findCommandParser.parse(commandText)).formatAsText();
    }
}
