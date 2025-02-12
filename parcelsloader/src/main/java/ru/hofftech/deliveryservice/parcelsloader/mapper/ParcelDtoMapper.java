package ru.hofftech.deliveryservice.parcelsloader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.request.CreateParcelCommandDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.response.ParcelDto;
import ru.hofftech.deliveryservice.parcelsloader.model.entity.ParcelEntity;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParcelDtoMapper {
    ParcelDtoMapper INSTANCE = Mappers.getMapper(ParcelDtoMapper.class);

    @Mapping(target = "id", ignore = true)
    ParcelEntity toEntity(CreateParcelCommandDto dto);

    ParcelDto toDto(ParcelEntity entity);

    List<ParcelDto> toDtoList(List<ParcelEntity> entities);
}
