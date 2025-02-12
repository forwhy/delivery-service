package ru.hofftech.deliveryservice.parcelsloader.service.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.parcelsloader.service.BillingAuditOutboxService;

@Component
@RequiredArgsConstructor
public class BillingAuditOutboxScheduler {

    private final BillingAuditOutboxService billingAuditOutboxService;

    @Scheduled(fixedRateString = "${scheduler.billing.fixed-rate}")
    public void send() {
        billingAuditOutboxService.sendUnprocessedBillingToKafka();
    }
}
