package ru.hofftech.deliveryservice.billing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hofftech.deliveryservice.billing.config.property.BillingProperties;
import ru.hofftech.deliveryservice.billing.enums.Operation;
import ru.hofftech.deliveryservice.billing.mapper.BillingAuditMapper;
import ru.hofftech.deliveryservice.billing.model.dto.BillingAuditDto;
import ru.hofftech.deliveryservice.billing.model.dto.BillingAuditResponseDto;
import ru.hofftech.deliveryservice.billing.model.dto.InboxDto;
import ru.hofftech.deliveryservice.billing.model.entity.BillingAuditEntity;
import ru.hofftech.deliveryservice.billing.model.entity.InboxEntity;
import ru.hofftech.deliveryservice.billing.repository.BillingAuditRepository;
import ru.hofftech.deliveryservice.billing.repository.InboxRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class BillingService {

    private static final Integer REPORT_CACHED_PERIOD_IN_DAYS = 30;
    private static final Integer END_DAY_HOUR = 23;
    private static final Integer END_DAY_MINUTE = 59;
    private static final Integer END_DAY_SECOND = 59;
    private final BillingAuditRepository billingAuditRepository;
    private final InboxRepository inboxRepository;
    private final BillingProperties billingProperties;

    /**
     * Метод сохраняет в БД информацию о счетах
     * @param inboxDto DTO сообщения для сохранения по паттерну Inbox Transaction
     */
    @Transactional
    public void saveBillingAudit(InboxDto inboxDto) {
        if (inboxRepository.existsById(inboxDto.id())) {
            log.error("Попытка сохранить уже обработанное сообщение {}", inboxDto.id());
            return;
        }
        saveBillingAudit(inboxDto.billingAuditDto());
        saveInbox(inboxDto.id());
    }

    /**
     * Метод возвращает все счета пользователя
     *
     * @param user Пользователь
     * @return Список счетов
     */
    public List<BillingAuditResponseDto> findBillingAuditRecordsByUser(String user) {
        try {
            List<BillingAuditEntity> billingAuditEntities = billingAuditRepository.findByUser(user);
            return collectReport(billingAuditEntities);
        } catch (Exception e) {
            log.error("Ошибка при попытке получить счета пользователя по логину: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Метод возвращает счета пользователя за определённый период
     *
     * @param user Пользователь
     * @param from Начало периода
     * @param to   Конец периода
     * @return Список счетов пользователя
     */
    public List<BillingAuditResponseDto> findBillingAuditRecordsByUserForPeriod(String user, LocalDate from, LocalDate to) {
        try {
            LocalDateTime fromDateTime = from.atStartOfDay();
            LocalDateTime toDateTime = to.atTime(END_DAY_HOUR, END_DAY_MINUTE, END_DAY_SECOND);

            List<BillingAuditEntity> billingAuditEntities = billingAuditRepository
                    .findByUserAndPeriod(user, fromDateTime, toDateTime);
            return collectReport(billingAuditEntities);
        } catch (Exception e) {
            log.error("Ошибка при попытке получить счета пользователя за период: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Метод возвращает счета пользователя за последние 30 дней из кэша
     *
     * @param user Пользователь
     * @return Список счетов пользователя за месяц
     */
    @Cacheable(value = "caffeine", key = "#user")
    public List<BillingAuditResponseDto> findBillingAuditRecordsByUserForLastMonth(String user) {
        try {
            LocalDateTime fromDateTime = LocalDate.now().atStartOfDay().minusDays(REPORT_CACHED_PERIOD_IN_DAYS);
            LocalDateTime toDateTime = LocalDate.now().atTime(END_DAY_HOUR, END_DAY_MINUTE, END_DAY_SECOND);

            List<BillingAuditEntity> billingAuditEntities = billingAuditRepository
                    .findByUserAndPeriod(user, fromDateTime, toDateTime);
            return collectReport(billingAuditEntities);
        } catch (Exception e) {
            log.error("Ошибка при попытке получить счета пользователя за последний месяц: {}", e.getMessage());
            throw e;
        }
    }

    private List<BillingAuditResponseDto> collectReport(List<BillingAuditEntity> billingAuditEntities) {
        if (billingAuditEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return BillingAuditMapper.INSTANCE.toBillingAuditResponseDtoList(billingAuditEntities);
    }

    private BigDecimal calculateAmount(Operation operationType, Integer volumeUsed) {
        BigDecimal pricePerSegment = operationType == Operation.LOAD_PARCELS
                ? billingProperties.price().loading()
                : billingProperties.price().unloading();

        return pricePerSegment.multiply(new BigDecimal(volumeUsed));
    }

    private void saveBillingAudit(BillingAuditDto dto) {
        BillingAuditEntity billingAudit = BillingAuditEntity.builder()
                .user(dto.user())
                .createdAt(dto.createdAt())
                .operationType(dto.operationType().getOperationName())
                .trucksCount(dto.trucksCount())
                .parcelsCount(dto.parcelsCount())
                .volumeUsed(dto.volumeUsed())
                .amount(calculateAmount(dto.operationType(), dto.volumeUsed()))
                .build();
        billingAuditRepository.save(billingAudit);
    }

    private void saveInbox(UUID inboxId) {
        InboxEntity entity = InboxEntity.builder()
                .id(inboxId)
                .createdAt(LocalDateTime.now())
                .build();
        inboxRepository.save(entity);
    }
}
