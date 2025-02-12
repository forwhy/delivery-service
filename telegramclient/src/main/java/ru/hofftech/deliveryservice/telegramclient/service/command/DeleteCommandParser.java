package ru.hofftech.deliveryservice.telegramclient.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.telegramclient.exception.InvalidCommandException;
import ru.hofftech.deliveryservice.telegramclient.service.validation.DeleteParcelCommandValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteCommandParser {

    private static final String COMMAND_PATTERN = "/delete \"([^\"]+)\"";
    private static final Integer NAME_INDEX = 1;
    private final DeleteParcelCommandValidator deleteParcelCommandValidator;

    public String parse(String commandText) throws InvalidCommandException {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(commandText);

        if (!matcher.matches()) {
            throw new InvalidCommandException(commandText);
        }

        String parcelName = matcher.group(NAME_INDEX);
        deleteParcelCommandValidator.validateName(parcelName);

        return parcelName;
    }
}
