package ru.hofftech.parcelsloader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.hofftech.parcelsloader.model.BillingAudit;
import ru.hofftech.parcelsloader.model.entity.BillingAuditEntity;

@Mapper
public interface BillingAuditMapper {

    BillingAuditMapper INSTANCE = Mappers.getMapper(BillingAuditMapper.class);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "operationType", source = "operationType")
    @Mapping(target = "parcelsCount", source = "parcelsCount")
    @Mapping(target = "trucksCount", source = "trucksCount")
    @Mapping(target = "amount", source = "amount")
    BillingAudit toBillingAudit(BillingAuditEntity entity);
}
