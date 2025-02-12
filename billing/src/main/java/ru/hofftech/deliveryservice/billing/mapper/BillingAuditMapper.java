package ru.hofftech.deliveryservice.billing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.hofftech.deliveryservice.billing.model.dto.BillingAuditResponseDto;
import ru.hofftech.deliveryservice.billing.model.entity.BillingAuditEntity;

import java.util.List;

@Mapper
public interface BillingAuditMapper {

    BillingAuditMapper INSTANCE = Mappers.getMapper(BillingAuditMapper.class);

    BillingAuditResponseDto toBillingAuditResponseDto(BillingAuditEntity billingAuditEntity);

    List<BillingAuditResponseDto> toBillingAuditResponseDtoList(List<BillingAuditEntity> billingAuditEntities);
}
