package ru.hofftech.deliveryservice.parcelsloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hofftech.deliveryservice.parcelsloader.enums.BillingAuditOutboxStatus;
import ru.hofftech.deliveryservice.parcelsloader.model.entity.BillingAuditOutboxEntity;

import java.util.List;

@Repository
public interface BillingAuditOutboxRepository extends JpaRepository<BillingAuditOutboxEntity, Long> {

    List<BillingAuditOutboxEntity> findAllByStatus(BillingAuditOutboxStatus status);
}
