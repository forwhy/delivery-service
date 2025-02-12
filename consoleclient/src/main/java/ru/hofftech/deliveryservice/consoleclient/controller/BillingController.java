package ru.hofftech.deliveryservice.consoleclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.deliveryservice.consoleclient.model.dto.BillingAuditResponseDto;
import ru.hofftech.deliveryservice.consoleclient.service.BillingClient;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BillingController {

    private final BillingClient billingClient;

    @ShellMethod(key = "/billing", value = "Получить счета по имени пользователя")
    public String findByUser(@ShellOption(value = "u") String user) {
        List<BillingAuditResponseDto> billings = billingClient.findByUser(user);
        if (billings.isEmpty()) {
            return String.format("Для клиента %s пока нет счетов", user);
        }

        return billings.stream()
                .map(BillingAuditResponseDto::formatAsReportLine)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
