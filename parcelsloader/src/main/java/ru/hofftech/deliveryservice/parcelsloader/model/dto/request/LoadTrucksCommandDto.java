package ru.hofftech.deliveryservice.parcelsloader.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

/**
 * DTO команды на погрузку посылок в грузовики
 * @param user Пользователь
 * @param parcelsText Список посылок текстом (опционально)
 * @param parcelsFile Путь к файлу посылок (опционально)
 * @param trucks Параметры грузовиков для транспортировки
 * @param type Алгоритм погрузки
 * @param out Формат вывода результата
 * @param outFilename Имя файла для вывода результата (опционально)
 */
public record LoadTrucksCommandDto(
        @NotBlank(message = "Пользователь должен быть указан")
        String user,
        @Nullable
        String parcelsText,
        @Nullable
        String parcelsFile,
        @NotBlank(message = "Параметры грузовиков должны быть заданы")
        String trucks,
        @NotBlank(message = "Алгоритм погрузки должен быть задан")
        String type,
        @NotBlank(message = "Формат вывода результата должен быть задан")
        String out,
        @Nullable
        String outFilename) {
}
