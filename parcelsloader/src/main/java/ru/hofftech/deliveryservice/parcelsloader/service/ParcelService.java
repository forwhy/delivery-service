package ru.hofftech.deliveryservice.parcelsloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.deliveryservice.parcelsloader.exception.ParcelNameConstraintViolationException;
import ru.hofftech.deliveryservice.parcelsloader.exception.ParcelNotExistException;
import ru.hofftech.deliveryservice.parcelsloader.mapper.ParcelDtoMapper;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.request.CreateParcelCommandDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.request.EditParcelCommandDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.response.ParcelDto;
import ru.hofftech.deliveryservice.parcelsloader.model.entity.ParcelEntity;
import ru.hofftech.deliveryservice.parcelsloader.repository.ParcelRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ParcelService {

    private static final Integer FIRST_SYMBOL_INDEX = 0;
    private final Long defaultLimit;
    private final ParcelRepository parcelRepository;

    /**
     * Создаёт посылку из запроса
     * @param createParcelCommandDto DTO посылки
     * @return DTO созданной посылки
     */
    public ParcelDto createParcel(CreateParcelCommandDto createParcelCommandDto) {
        Optional<ParcelEntity> existingParcel = parcelRepository.findByName(createParcelCommandDto.name());
        existingParcel.ifPresent(parcel -> {
            log.error("Попытка создать уже существующую посылку: {}", createParcelCommandDto.name());
            throw new ParcelNameConstraintViolationException(
                    String.format("Попытка создать уже существующую посылку: %s", createParcelCommandDto.name()));
        });

        ParcelEntity parcelEntity = ParcelDtoMapper.INSTANCE.toEntity(createParcelCommandDto);
        return ParcelDtoMapper.INSTANCE.toDto(parcelRepository.save(parcelEntity));
    }

    /**
     * Ищет посылку по имени
     * @param parcelName Название посылки
     * @return Найденная посылка
     */
    public ParcelDto findParcelById(String parcelName) {
        ParcelEntity parcel = parcelRepository.findByName(parcelName)
                .orElseThrow(() -> {
                    log.error("Попытка найти несуществующую посылку: {}", parcelName);
                    return new ParcelNotExistException(String.format("Посылка с именем %s не существует", parcelName));
                });
        return ParcelDtoMapper.INSTANCE.toDto(parcel);
    }

    /**
     * Возвращает список всех посылок с ограничением по количеству на вывод
     * @param limit Максимальное количество посылок для возврата
     * @param offset Смещение от начала
     * @return Список посылок
     */
    public List<ParcelDto> findAllParcels(Long limit, Long offset) {
        List<ParcelEntity> parcels = parcelRepository
                .findAllWithLimitAndOffset(
                        limit == null ? defaultLimit : limit,
                        offset == null ? 0 : offset);
        return ParcelDtoMapper.INSTANCE.toDtoList(parcels);
    }

    /**
     * Редактирует посылку, если она существует
     * @param originalParcelName Название посылки для редактирования
     * @param editParcelCommandDto Новые данные о посылке
     * @return Обновлённая посылка
     */
    public ParcelDto editParcel(String originalParcelName, EditParcelCommandDto editParcelCommandDto) {
        ParcelEntity parcel = parcelRepository.findByName(originalParcelName)
                .orElseThrow(() -> {
                    log.error("Попытка обновить несуществующую посылку: {}", originalParcelName);
                    return new ParcelNotExistException(
                            String.format("Попытка обновить несуществующую посылку: %s", originalParcelName));
                });
        if (parcelRepository.existsByName(editParcelCommandDto.name())) {
            log.error("Попытка создать уже существующую посылку: {}", editParcelCommandDto.name());
            throw new ParcelNameConstraintViolationException(
                    String.format("Посылка с таким названием уже существует: %s", editParcelCommandDto.name()));
        }
        parcel.setName(editParcelCommandDto.name());
        parcel.setSymbol(editParcelCommandDto.symbol().charAt(FIRST_SYMBOL_INDEX));
        parcel.setForm(editParcelCommandDto.form());
        parcelRepository.save(parcel);
        return ParcelDtoMapper.INSTANCE.toDto(parcel);
    }

    /**
     * Удаляет посылку, если она существует
     * @param parcelName Название посылки для удаления
     */
    public void deleteParcel(String parcelName) {
        ParcelEntity parcel = parcelRepository.findByName(parcelName)
                .orElseThrow(() -> {
                    log.error("Попытка удалить несуществующую посылку: {}", parcelName);
                    return new ParcelNotExistException(
                            String.format("Попытка удалить несуществующую посылку: %s", parcelName));
                });
        parcelRepository.delete(parcel);
    }
}
