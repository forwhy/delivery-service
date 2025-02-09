package ru.hofftech.telegramclient.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.telegramclient.exception.InvalidCommandException;
import ru.hofftech.telegramclient.service.validation.BillingCommandValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingCommandParser {

    private static final String COMMAND_PATTERN = "/billing --u \"([^\"]+)\"";
    private static final Integer USER_INDEX = 1;
    private final BillingCommandValidator billingCommandValidator;

    public String parse(String commandText) throws InvalidCommandException {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(commandText);

        if (!matcher.matches()) {
            throw new InvalidCommandException(commandText);
        }

        String user = matcher.group(USER_INDEX);
        billingCommandValidator.validateCommandArguments(user);

        return user;
    }
}
