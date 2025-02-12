package ru.hofftech.deliveryservice.telegramclient.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.telegramclient.exception.InvalidCommandException;
import ru.hofftech.deliveryservice.telegramclient.model.dto.UnloadTrucksCommandDto;
import ru.hofftech.deliveryservice.telegramclient.service.validation.UnloadTrucksCommandValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnloadCommandParser {

    private static final String COMMAND_PATTERN = "/unload --u \"([^\"]+)\" " +
            "--infile \"([^\"]+)\" " +
            "--outfile \"([^\"]+)\"" +
            "(?: --withcount)?";
    private static final Integer USER_INDEX = 1;
    private static final Integer SOURCE_FILE_NAME_INDEX = 2;
    private static final Integer TARGET_FILE_NAME_INDEX = 3;
    private final UnloadTrucksCommandValidator unloadTrucksCommandValidator;

    public UnloadTrucksCommandDto parse(String commandText) throws InvalidCommandException {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(commandText);

        if (!matcher.matches()) {
            throw new InvalidCommandException(commandText);
        }

        String user = matcher.group(USER_INDEX);
        String inFile = matcher.group(SOURCE_FILE_NAME_INDEX);
        String outFile = matcher.group(TARGET_FILE_NAME_INDEX);
        Boolean withCount = commandText.contains("--withcount");

        unloadTrucksCommandValidator.validateCommandArguments(user, inFile, outFile);

        return new UnloadTrucksCommandDto(user, inFile, outFile, withCount);
    }
}
