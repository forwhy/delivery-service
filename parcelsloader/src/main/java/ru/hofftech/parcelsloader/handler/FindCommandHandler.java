package ru.hofftech.parcelsloader.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcelsloader.mapper.ParcelMapper;
import ru.hofftech.parcelsloader.model.entity.ParcelEntity;
import ru.hofftech.parcelsloader.repository.ParcelRepository;
import ru.hofftech.parcelsloader.service.validation.FindParcelCommandValidator;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class FindCommandHandler {

    private final FindParcelCommandValidator findParcelCommandValidator;
    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;

    public String executeCommand(String parcelName) {
        try {
            findParcelCommandValidator.validate(parcelName);

            Optional<ParcelEntity> parcel = parcelRepository.findByName(parcelName);
            if (parcel.isEmpty()) {
                log.error("Попытка найти несуществующую посылку: {}", parcelName);
                return String.format("Посылка с названием %s не найдена", parcelName);
            }
            return parcelMapper.toModel(parcel.get()).toString();
        } catch (Exception e) {
            log.error("Ошибка при попытке найти посылку: {}", e.getMessage());
            return String.format("Ошибка при попытке найти посылку: %s", e.getMessage());
        }
    }
}
