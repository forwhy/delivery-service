package ru.hofftech.parcelsloader.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcelsloader.mapper.ParcelMapper;
import ru.hofftech.parcelsloader.model.dto.request.EditParcelCommandDto;
import ru.hofftech.parcelsloader.model.entity.ParcelEntity;
import ru.hofftech.parcelsloader.repository.ParcelRepository;
import ru.hofftech.parcelsloader.service.validation.EditParcelCommandValidator;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class EditCommandHandler {

    private static final Integer FIRST_SYMBOL_INDEX = 0;
    private final EditParcelCommandValidator editParcelCommandValidator;
    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;

    public String executeCommand(EditParcelCommandDto commandDto) {
        try {
            editParcelCommandValidator.validate(commandDto);

            Optional<ParcelEntity> originalParcel = parcelRepository.findByName(commandDto.currentId());
            if (originalParcel.isEmpty()) {
                log.error("Попытка обновить несуществующую посылку: {}", commandDto.name());
                return String.format("Посылка с названием %s не найдена", commandDto.currentId());
            }

            Optional<ParcelEntity> existingParcel = parcelRepository.findByName(commandDto.name());
            if (!existingParcel.isEmpty()) {
                log.error("Попытка создать уже существующую посылку: {}", commandDto.name());
                return String.format("Посылка с таким названием уже существует: %s", commandDto.name());
            }

            var parcel = originalParcel.get();
            parcel.setName(commandDto.name());
            parcel.setSymbol(commandDto.symbol().charAt(FIRST_SYMBOL_INDEX));
            parcel.setForm(commandDto.form());
            parcelRepository.save(parcel);
            return parcelMapper.toModel(parcel).toString();
        }
        catch (Exception e) {
            log.error("Ошибка при попытке обновить посылку: {}", e.getMessage());
            return String.format("Ошибка при попытке обновить посылку: %s", e.getMessage());
        }
    }
}
