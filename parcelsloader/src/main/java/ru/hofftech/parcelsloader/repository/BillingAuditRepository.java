package ru.hofftech.parcelsloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hofftech.parcelsloader.model.entity.BillingAuditEntity;

import java.util.List;

@Repository
public interface BillingAuditRepository extends JpaRepository<BillingAuditEntity, Long> {

    List<BillingAuditEntity> findByUser(String user);
}
