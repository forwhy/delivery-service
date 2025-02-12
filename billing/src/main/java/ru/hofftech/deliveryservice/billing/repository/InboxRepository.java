package ru.hofftech.deliveryservice.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hofftech.deliveryservice.billing.model.entity.InboxEntity;

import java.util.UUID;

public interface InboxRepository extends JpaRepository<InboxEntity, UUID> {
}
