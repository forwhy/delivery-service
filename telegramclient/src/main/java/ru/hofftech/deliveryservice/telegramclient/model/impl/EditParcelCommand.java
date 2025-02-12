package ru.hofftech.deliveryservice.telegramclient.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.model.dto.EditParcelCommandDto;
import ru.hofftech.deliveryservice.telegramclient.service.ParcelsLoaderClient;
import ru.hofftech.deliveryservice.telegramclient.service.command.EditCommandParser;

@Component
@RequiredArgsConstructor
public class EditParcelCommand implements Command {

    private final EditCommandParser editCommandParser;
    private final ParcelsLoaderClient parcelsLoaderClient;

    @Override
    public String execute(String commandText) {
        EditParcelCommandDto editParcelCommandDto = editCommandParser.parse(commandText);
        return parcelsLoaderClient
                .updateParcel(editParcelCommandDto.id(), editParcelCommandDto.parcel())
                .formatAsText();
    }
}
