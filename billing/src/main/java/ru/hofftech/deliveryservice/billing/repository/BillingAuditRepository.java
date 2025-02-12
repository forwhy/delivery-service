package ru.hofftech.deliveryservice.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hofftech.deliveryservice.billing.model.entity.BillingAuditEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillingAuditRepository extends JpaRepository<BillingAuditEntity, Long> {

    List<BillingAuditEntity> findByUser(String user);

    @Query("SELECT b from BillingAuditEntity b where b.user = :user and b.createdAt between :from and :to")
    List<BillingAuditEntity> findByUserAndPeriod(String user, LocalDateTime from, LocalDateTime to);
}
