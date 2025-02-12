package ru.hofftech.deliveryservice.telegramclient.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.telegramclient.exception.InvalidCommandException;
import ru.hofftech.deliveryservice.telegramclient.model.dto.CreateParcelCommandDto;
import ru.hofftech.deliveryservice.telegramclient.service.validation.CreateParcelCommandValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCommandParser {

    private static final String COMMAND_PATTERN = "/create --name \"([^\"]+)\" --form \"([^\"]+)\" --symbol \"([^\"]+)\"";
    private static final Integer NAME_INDEX = 1;
    private static final Integer FORM_INDEX = 2;
    private static final Integer SYMBOL_INDEX = 3;
    private static final Integer FIRST_SYMBOL_INDEX = 0;
    private final CreateParcelCommandValidator createParcelCommandValidator;

    public CreateParcelCommandDto parse(String commandText) throws InvalidCommandException {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(commandText);

        if (!matcher.matches()) {
            throw new InvalidCommandException(commandText);
        }

        String name = matcher.group(NAME_INDEX);
        String form = matcher.group(FORM_INDEX);
        String symbolString = matcher.group(SYMBOL_INDEX);
        createParcelCommandValidator.validateCommandArguments(name, form, symbolString);

        return new CreateParcelCommandDto(
                name,
                form,
                symbolString.charAt(FIRST_SYMBOL_INDEX));
    }
}
