package ru.hofftech.parcelsloader.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcelsloader.model.entity.ParcelEntity;
import ru.hofftech.parcelsloader.repository.ParcelRepository;
import ru.hofftech.parcelsloader.service.validation.DeleteParcelCommandValidator;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class DeleteCommandHandler {

    private final DeleteParcelCommandValidator deleteParcelCommandValidator;
    private final ParcelRepository parcelRepository;

    public String executeCommand(String parcelName) {
        try {
            deleteParcelCommandValidator.validate(parcelName);

            Optional<ParcelEntity> originalParcel = parcelRepository.findByName(parcelName);
            if (originalParcel.isEmpty()) {
                log.error("Попытка удалить несуществующую посылку: {}", parcelName);
                return String.format("Посылка с названием %s не найдена", parcelName);
            }
            parcelRepository.delete(originalParcel.get());
            return String.format("Посылка с названием %s успешно удалена", parcelName);
        } catch (Exception e) {
            log.error("Ошибка при попытке удалить посылку: {}", e.getMessage());
            return String.format("Ошибка при попытке удалить посылку: %s", e.getMessage());
        }
    }
}
