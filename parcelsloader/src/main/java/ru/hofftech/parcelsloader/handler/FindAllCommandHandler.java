package ru.hofftech.parcelsloader.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.hofftech.parcelsloader.mapper.ParcelMapper;
import ru.hofftech.parcelsloader.model.dto.request.FindAllParcelCommandDto;
import ru.hofftech.parcelsloader.model.entity.ParcelEntity;
import ru.hofftech.parcelsloader.repository.ParcelRepository;
import ru.hofftech.parcelsloader.service.validation.FindAllParcelCommandValidator;

import java.util.List;

@Slf4j
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class FindAllCommandHandler {

    private final Long defaultLimit;
    private final FindAllParcelCommandValidator findAllParcelCommandValidator;
    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;

    public String executeCommand(FindAllParcelCommandDto commandDto) {
        try {
            findAllParcelCommandValidator.validate(commandDto);

            StringBuilder result = new StringBuilder();
            List<ParcelEntity> parcels = parcelRepository
                    .findAllWithLimitAndOffset(
                            commandDto.limit().isEmpty() ? defaultLimit : commandDto.limit().get(),
                            commandDto.offset().isEmpty() ? 0 : commandDto.offset().get());

            for (ParcelEntity parcel : parcels) {
                result.append(parcelMapper.toModel(parcel));
            }
            return result.toString();
        } catch (Exception e) {
            log.error("Ошибка при попытке получить посылки: {}", e.getMessage());
            return String.format("Ошибка при попытке получить посылки: %s", e.getMessage());
        }
    }
}
