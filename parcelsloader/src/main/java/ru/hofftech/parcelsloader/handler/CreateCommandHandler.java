package ru.hofftech.parcelsloader.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcelsloader.mapper.ParcelMapper;
import ru.hofftech.parcelsloader.model.Parcel;
import ru.hofftech.parcelsloader.model.dto.request.CreateParcelCommandDto;
import ru.hofftech.parcelsloader.model.entity.ParcelEntity;
import ru.hofftech.parcelsloader.repository.ParcelRepository;
import ru.hofftech.parcelsloader.service.validation.CreateParcelCommandValidator;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CreateCommandHandler {

    private static final Integer FIRST_SYMBOL_INDEX = 0;
    private final CreateParcelCommandValidator createParcelCommandValidator;
    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;

    public String executeCommand(CreateParcelCommandDto commandDto) {
        try {
            createParcelCommandValidator.validate(commandDto);

            Optional<ParcelEntity> existingParcel = parcelRepository.findByName(commandDto.name());
            if (!existingParcel.isEmpty()) {
                log.error("Попытка создать уже существующую посылку: {}", commandDto.name());
                return String.format("Посылка с таким названием уже существует: %s", commandDto.name());
            }

            ParcelEntity entity = parcelRepository.save(
                    new ParcelEntity(commandDto.name(), commandDto.symbol().charAt(FIRST_SYMBOL_INDEX), commandDto.form()));
            Parcel newParcel = parcelMapper.entityToModel(entity);

            return newParcel.toString();
        } catch (Exception e) {
            log.error("Ошибка при попытке создать посылку: {}", e.getMessage());
            return String.format("Ошибка при попытке создать посылку: %s", e.getMessage());
        }
    }
}
