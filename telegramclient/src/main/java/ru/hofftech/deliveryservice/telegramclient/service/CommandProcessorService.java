package ru.hofftech.deliveryservice.telegramclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.telegramclient.exception.InvalidCommandException;
import ru.hofftech.deliveryservice.telegramclient.model.Command;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandProcessorService {

    private static final Integer COMMAND_KEY_WORD_INDEX = 0;
    private static final String COMMAND_TEXT_DELIMITER = " ";
    private final Map<String, Command> commands;

    public String processCommandText(String command) {
        validateCommandText(command);
        String keyWord = command.split(COMMAND_TEXT_DELIMITER)[COMMAND_KEY_WORD_INDEX];

        return extractCommandByKeyWord(keyWord).execute(command);
    }

    private void validateCommandText(String commandText) {
        if (commandText == null || commandText.isEmpty()) {
            throw new InvalidCommandException("");
        }
    }

    private Command extractCommandByKeyWord(String commandKeyWord) {
        if (!commands.containsKey(commandKeyWord)) {
            throw new InvalidCommandException(commandKeyWord);
        }
        return commands.get(commandKeyWord);
    }
}
