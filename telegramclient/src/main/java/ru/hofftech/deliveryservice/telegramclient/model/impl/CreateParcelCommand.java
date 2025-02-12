package ru.hofftech.deliveryservice.telegramclient.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.service.ParcelsLoaderClient;
import ru.hofftech.deliveryservice.telegramclient.service.command.CreateCommandParser;

@Component
@RequiredArgsConstructor
public class CreateParcelCommand implements Command {

    private final CreateCommandParser createCommandParser;
    private final ParcelsLoaderClient parcelsLoaderClient;

    @Override
    public String execute(String commandText) {
        return parcelsLoaderClient.createParcel(createCommandParser.parse(commandText)).formatAsText();
    }
}
