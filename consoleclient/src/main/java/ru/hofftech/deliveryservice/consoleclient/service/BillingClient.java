package ru.hofftech.deliveryservice.consoleclient.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import ru.hofftech.deliveryservice.consoleclient.model.dto.BillingAuditResponseDto;
import java.util.List;

public interface BillingClient {

    @GetExchange("/api/v1/billing")
    List<BillingAuditResponseDto> findByUser(@RequestParam String user);
}
