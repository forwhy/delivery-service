package ru.hofftech.deliveryservice.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.deliveryservice.billing.model.dto.BillingAuditResponseDto;
import ru.hofftech.deliveryservice.billing.service.BillingService;

import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер с операциями по биллингу
 */
@RestController
@Tag(name = "BillingController", description = "Операции, связанные с биллингом")
@RequestMapping(path = "/api/v1/billing", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BillingController {

    private static final Integer REPORT_CACHED_PERIOD_IN_DAYS = 30;
    private final BillingService billingService;

    @Validated
    @Operation(
            summary = "Получить счета по имени клиента и периоду",
            description = "Возвращает список счетов по указанному имени клиента и периоду")
    @GetMapping()
    public List<BillingAuditResponseDto> findByUserPeriod(
            @RequestParam @NonNull @NotBlank String user,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return billingService.findBillingAuditRecordsByUser(user);
        }
        if (endDate.equals(LocalDate.now()) && startDate.equals(LocalDate.now().minusDays(REPORT_CACHED_PERIOD_IN_DAYS))) {
            return billingService.findBillingAuditRecordsByUserForLastMonth(user);
        } else {
            return billingService.findBillingAuditRecordsByUserForPeriod(user, startDate, endDate);
        }
    }
}
