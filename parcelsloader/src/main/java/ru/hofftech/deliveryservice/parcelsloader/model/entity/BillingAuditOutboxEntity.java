package ru.hofftech.deliveryservice.parcelsloader.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.hofftech.deliveryservice.parcelsloader.enums.BillingAuditOutboxStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "billing_audit_outbox")
public class BillingAuditOutboxEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "\"user\"")
    private String user;

    private LocalDateTime createdAt;

    private String operationType;

    private Integer volumeUsed;

    private Integer parcelsCount;

    private Integer trucksCount;

    @Enumerated(EnumType.STRING)
    private BillingAuditOutboxStatus status;
}
