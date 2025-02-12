package ru.hofftech.deliveryservice.telegramclient.model.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.model.dto.response.BillingAuditResponseDto;
import ru.hofftech.deliveryservice.telegramclient.service.BillingClient;
import ru.hofftech.deliveryservice.telegramclient.service.command.BillingCommandParser;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindBillingsByUserCommand implements Command {

    private final BillingCommandParser billingCommandParser;
    private final BillingClient billingClient;

    @Override
    public String execute(String commandText) {
        return billingClient.findByUser(billingCommandParser.parse(commandText)).stream()
                .map(BillingAuditResponseDto::formatAsReportLine)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
