package ru.hofftech.deliveryservice.parcelsloader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.hofftech.deliveryservice.parcelsloader.enums.Operation;
import ru.hofftech.deliveryservice.parcelsloader.exception.InvalidOperationException;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.OutboxDto;
import ru.hofftech.deliveryservice.parcelsloader.model.entity.BillingAuditOutboxEntity;


@Mapper
public interface BillingAuditMapper {

    BillingAuditMapper INSTANCE = Mappers.getMapper(BillingAuditMapper.class);

    @Mapping(target = "billingAuditDto.user", source = "user")
    @Mapping(target = "billingAuditDto.createdAt", source = "createdAt")
    @Mapping(target = "billingAuditDto.operationType", source = "operationType")
    @Mapping(target = "billingAuditDto.parcelsCount", source = "parcelsCount")
    @Mapping(target = "billingAuditDto.trucksCount", source = "trucksCount")
    @Mapping(target = "billingAuditDto.volumeUsed", source = "volumeUsed")
    OutboxDto toOutboxDto(BillingAuditOutboxEntity entity);

    default Operation defineOperationByName(String operationName) {
        for (Operation operation : Operation.values()) {
            if (operation.getOperationName().equals(operationName)) {
                return operation;
            }
        }
        throw new InvalidOperationException("Неизвестная операция: " + operationName);
    }
}
