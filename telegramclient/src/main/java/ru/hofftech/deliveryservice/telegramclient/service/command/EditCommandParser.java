package ru.hofftech.deliveryservice.telegramclient.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.telegramclient.exception.InvalidCommandException;
import ru.hofftech.deliveryservice.telegramclient.model.dto.EditParcelCommandDto;
import ru.hofftech.deliveryservice.telegramclient.model.dto.Parcel;
import ru.hofftech.deliveryservice.telegramclient.service.validation.EditParcelCommandValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditCommandParser {

    private static final String COMMAND_PATTERN =
            "/edit \"([^\"]+)\" --name \"([^\"]+)\" --form \"([^\"]+)\" --symbol \"([^\"]+)\"";
    private static final Integer ID_INDEX = 1;
    private static final Integer NAME_INDEX = 2;
    private static final Integer FORM_INDEX = 3;
    private static final Integer SYMBOL_INDEX = 4;
    private static final Integer FIRST_SYMBOL_INDEX = 0;
    private final EditParcelCommandValidator editParcelCommandValidator;

    public EditParcelCommandDto parse(String commandText) throws InvalidCommandException {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(commandText);

        if (!matcher.matches()) {
            throw new InvalidCommandException(commandText);
        }

        editParcelCommandValidator.validateCommandArguments(
                matcher.group(ID_INDEX),
                matcher.group(NAME_INDEX),
                matcher.group(FORM_INDEX),
                matcher.group(SYMBOL_INDEX));

        return new EditParcelCommandDto(
                matcher.group(ID_INDEX),
                new Parcel(matcher.group(NAME_INDEX),
                                        matcher.group(FORM_INDEX),
                                        matcher.group(SYMBOL_INDEX).charAt(FIRST_SYMBOL_INDEX)));
    }
}
