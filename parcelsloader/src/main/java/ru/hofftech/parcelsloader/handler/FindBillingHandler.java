package ru.hofftech.parcelsloader.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcelsloader.mapper.BillingAuditMapper;
import ru.hofftech.parcelsloader.model.entity.BillingAuditEntity;
import ru.hofftech.parcelsloader.repository.BillingAuditRepository;
import ru.hofftech.parcelsloader.service.validation.FindBillingCommandValidator;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FindBillingHandler {

    private final FindBillingCommandValidator findBillingCommandValidator;
    private final BillingAuditRepository billingAuditRepository;

    public String executeCommand(String user) {
        try {
            findBillingCommandValidator.validate(user);
            List<BillingAuditEntity> billingAuditEntities = billingAuditRepository.findByUser(user);

            return collectReport(billingAuditEntities);
        } catch (Exception e) {
            log.error("Ошибка при попытке получить квитанции пользователя: {}", e.getMessage());
            return String.format("Ошибка при попытке получить квитанции пользователя: %s", e.getMessage());
        }
    }

    private String collectReport(List<BillingAuditEntity> billingAuditEntities) {
        StringBuilder reportBuilder = new StringBuilder();

        for (BillingAuditEntity billingAuditEntity : billingAuditEntities) {
            reportBuilder
                    .append(BillingAuditMapper.INSTANCE.toBillingAudit(billingAuditEntity).toString())
                    .append(System.lineSeparator());
        }

        return reportBuilder.toString();
    }
}
