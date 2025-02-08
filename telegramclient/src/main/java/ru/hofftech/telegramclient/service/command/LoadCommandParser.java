package ru.hofftech.telegramclient.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.telegramclient.exception.InvalidCommandException;
import ru.hofftech.telegramclient.model.record.LoadTrucksCommand;
import ru.hofftech.telegramclient.service.validation.LoadTrucksCommandValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoadCommandParser {

    private static final String COMMAND_PATTERN =
            "/load --u \"([^\"]+)\"" +
                    "(?: --parcels-text \"([^\"]+)\")?" +
                    "(?: --parcels-file \"([^\"]+)\")?" +
                    " --trucks \"([^\"]+)\"" +
                    " --type \"([^\"]+)\"" +
                    " --out (text|json-file)" +
                    "(?: --out-filename \"([^\"]+)\")?";
    private static final Integer USER_INDEX = 1;
    private static final Integer PARCELS_TEXT_INDEX = 2;
    private static final Integer PARCELS_FILE_INDEX = 3;
    private static final Integer TRUCKS_VARIANTS_INDEX = 4;
    private static final Integer TYPE_INDEX = 5;
    private static final Integer OUT_TARGET_INDEX = 6;
    private static final Integer OUT_FILENAME_TARGET_INDEX = 7;
    private final LoadTrucksCommandValidator loadTrucksCommandValidator;

    public LoadTrucksCommand parse(String commandText) throws InvalidCommandException {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(commandText);

        if (!matcher.matches()) {
            throw new InvalidCommandException(commandText);
        }

        String user = matcher.group(USER_INDEX);
        String parcelsText = matcher.group(PARCELS_TEXT_INDEX);
        String parcelsFile = matcher.group(PARCELS_FILE_INDEX);
        String trucksVariant = matcher.group(TRUCKS_VARIANTS_INDEX);
        String type = matcher.group(TYPE_INDEX);
        String out = matcher.group(OUT_TARGET_INDEX);
        String outFilename = matcher.group(OUT_FILENAME_TARGET_INDEX);

        loadTrucksCommandValidator.validateCommandArguments(user,
                                                            parcelsText,
                                                            parcelsFile,
                                                            trucksVariant,
                                                            type,
                                                            out,
                                                            outFilename);

        return new LoadTrucksCommand(
                user,
                parcelsText,
                parcelsFile,
                trucksVariant,
                type,
                out,
                outFilename);
    }
}
