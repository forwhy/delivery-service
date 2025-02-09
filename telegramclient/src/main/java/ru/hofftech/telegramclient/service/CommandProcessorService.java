package ru.hofftech.telegramclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.telegramclient.enums.CommandType;
import ru.hofftech.telegramclient.model.record.EditParcelCommand;
import ru.hofftech.telegramclient.service.command.CommandRecognizer;
import ru.hofftech.telegramclient.service.command.CreateCommandParser;
import ru.hofftech.telegramclient.service.command.DeleteCommandParser;
import ru.hofftech.telegramclient.service.command.EditCommandParser;
import ru.hofftech.telegramclient.service.command.FindCommandParser;
import ru.hofftech.telegramclient.service.command.LoadCommandParser;
import ru.hofftech.telegramclient.service.command.UnloadCommandParser;

import static ru.hofftech.telegramclient.constants.Constant.HELP_TEXT;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandProcessorService {

    private final CommandRecognizer commandRecognizer;
    private final CreateCommandParser createCommandParser;
    private final DeleteCommandParser deleteCommandParser;
    private final EditCommandParser editCommandParser;
    private final FindCommandParser findCommandParser;
    private final LoadCommandParser loadCommandParser;
    private final UnloadCommandParser unloadCommandParser;
    private final ParcelsLoaderClient parcelsLoaderClient;

    public String processCommandText(String command) {
        try {
            CommandType commandType = commandRecognizer.recognizeCommand(command);

            return switch (commandType) {
                case CommandType.CREATE_PARCEL ->
                    parcelsLoaderClient.createParcel(createCommandParser.parse(command));
                case CommandType.DELETE_PARCEL ->
                    parcelsLoaderClient.deleteParcel(deleteCommandParser.parse(command));
                case CommandType.EDIT_PARCEL -> {
                    EditParcelCommand editParcelCommand = editCommandParser.parse(command);
                    yield parcelsLoaderClient.updateParcel(editParcelCommand.id(), editParcelCommand.createParcelCommand());
                }
                case CommandType.FIND_PARCEL ->
                    parcelsLoaderClient.findParcel(findCommandParser.parse(command));
                case CommandType.FIND_ALL_PARCELS ->
                    parcelsLoaderClient.findAll();
                case CommandType.LOAD_PARCELS ->
                    parcelsLoaderClient.load(loadCommandParser.parse(command));
                case CommandType.UNLOAD_PARCELS ->
                    parcelsLoaderClient.unload(unloadCommandParser.parse(command));
                case CommandType.HELP -> HELP_TEXT;
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
