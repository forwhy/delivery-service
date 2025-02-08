package ru.hofftech.telegramclient.service.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hofftech.telegramclient.enums.CommandType;
import ru.hofftech.telegramclient.exception.InvalidCommandException;

import java.util.Arrays;

/**
 * This class converts a text command key word into CommandType enum value (if possible)
 */
@Slf4j
@Component
public class CommandRecognizer {

    private static final Integer COMMAND_KEY_WORD_INDEX = 0;
    private static final String COMMAND_TEXT_DELIMITER = " ";

    /**
     * Method extracts command type from command text by key word
     * @param commandText Command text to recognize
     * @return Command type as CommandType enum value
     * @throws InvalidCommandException if command is not recognized
     */
    public CommandType recognizeCommand(String commandText) {
        validateCommandText(commandText);
        String keyWord = commandText.split(COMMAND_TEXT_DELIMITER)[COMMAND_KEY_WORD_INDEX];

        return extractCommandByKeyWord(keyWord);
    }

    private void validateCommandText(String commandText) {
        if (commandText == null || commandText.isEmpty()) {
            throw new InvalidCommandException("");
        }
    }

    private CommandType extractCommandByKeyWord(String commandKeyWord) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.getCommandKeyWord().equals(commandKeyWord))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException(commandKeyWord));
    }
}
