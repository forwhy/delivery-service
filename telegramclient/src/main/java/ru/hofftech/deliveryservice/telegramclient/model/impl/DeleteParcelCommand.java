package ru.hofftech.deliveryservice.telegramclient.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.service.ParcelsLoaderClient;
import ru.hofftech.deliveryservice.telegramclient.service.command.DeleteCommandParser;

@Component
@RequiredArgsConstructor
public class DeleteParcelCommand implements Command {

    private final DeleteCommandParser deleteCommandParser;
    private final ParcelsLoaderClient parcelsLoaderClient;

    @Override
    public String execute(String commandText) {
        parcelsLoaderClient.deleteParcel(deleteCommandParser.parse(commandText));
        return "Посылка успешно удалена";
    }
}
