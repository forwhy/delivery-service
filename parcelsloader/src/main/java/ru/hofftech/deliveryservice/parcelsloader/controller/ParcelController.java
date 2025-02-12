package ru.hofftech.deliveryservice.parcelsloader.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.request.CreateParcelCommandDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.request.EditParcelCommandDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.response.ParcelDto;
import ru.hofftech.deliveryservice.parcelsloader.service.ParcelService;

import java.util.List;

@RestController
@Tag(name = "ParcelController", description = "Контроллер для работы с посылками")
@RequestMapping(path = "/api/v1/parcels", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @Validated
    @Operation(
            summary = "Найти посылку по названию",
            description = "Находит и возвращает посылку по её названию")
    @GetMapping("/{parcelId}")
    public ParcelDto find(@PathVariable @NotBlank(message = "Название посылки должно быть заполнено") String parcelId) {
        return parcelService.findParcelById(parcelId);
    }

    @Validated
    @Operation(
            summary = "Получить список всех посылок",
            description = "Получает список всех посылок с постраничным ограничением")
    @GetMapping()
    public List<ParcelDto> findAll(
            @RequestParam(required = false)
            @Min(0) @Max(value = 1000, message = "")
            Long limit,
            @RequestParam(required = false)
            @Min(0)
            Long offset) {
        return parcelService.findAllParcels(limit, offset);
    }

    @Validated
    @Operation(
            summary = "Создать новую посылку",
            description = "Создаёт и возвращает созданную посылку")
    @PostMapping()
    public ParcelDto create(@RequestBody CreateParcelCommandDto dto) {
        return parcelService.createParcel(dto);
    }

    @Validated
    @Operation(
            summary = "Редактировать посылку",
            description = "Редактирует и возвращает обновлённую посылку")
    @PutMapping("/{parcelId}")
    public ParcelDto update(@PathVariable String parcelId,
                            @RequestBody EditParcelCommandDto dto) {
        return parcelService.editParcel(parcelId, dto);
    }

    @Validated
    @Operation(
            summary = "Удалить посылку",
            description = "Удаляет посылку")
    @DeleteMapping("/{parcelId}")
    public void delete(@PathVariable @NotBlank(message = "Название посылки к удалению должно быть задано") String parcelId) {
        parcelService.deleteParcel(parcelId);
    }
}
