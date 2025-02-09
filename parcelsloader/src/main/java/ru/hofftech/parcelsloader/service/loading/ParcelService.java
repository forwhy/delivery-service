package ru.hofftech.parcelsloader.service.loading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.parcelsloader.enums.LoadInputMode;
import ru.hofftech.parcelsloader.exception.InvalidParcelException;
import ru.hofftech.parcelsloader.mapper.ParcelMapper;
import ru.hofftech.parcelsloader.model.Parcel;
import ru.hofftech.parcelsloader.model.entity.ParcelEntity;
import ru.hofftech.parcelsloader.repository.ParcelRepository;
import ru.hofftech.parcelsloader.util.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParcelService {

    private static final String PARCEL_NAMES_DELIMITER = "%n";
    private final FileReader fileReader;
    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;

    public List<Parcel> extractParcels(LoadInputMode inputMode, String parcelsSource) {
        List<String> parcelNames = switch (inputMode) {
            case TEXT -> extractParcelNamesFromText(parcelsSource);
            case FILE -> extractParcelNamesFromFile(parcelsSource);
        };

        List<Parcel> parcels = new ArrayList<>();

        for (String parcelName : parcelNames) {
            Optional<ParcelEntity> parcelEntity = parcelRepository.findByName(parcelName);
            if (parcelEntity.isEmpty()) {
                log.error("Попытка погрузить не существующую посылку: {}", parcelName);
                throw new InvalidParcelException(
                        String.format("Попытка погрузить не существующую посылку: %s", parcelName));
            }
            parcels.add(parcelMapper.entityToModel(parcelEntity.get()));
        }

        return parcels;
    }

    private List<String> extractParcelNamesFromFile(String filePath) {
        var fileLines = fileReader.readAllLines(filePath);

        if (fileLines.isEmpty()) {
            throw new IllegalArgumentException(String.format("Файл %s не содержит данных для обработки", filePath));
        }

        return fileLines.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private List<String> extractParcelNamesFromText(String text) {
        return Arrays.stream(text.split(PARCEL_NAMES_DELIMITER)).toList();
    }
}
