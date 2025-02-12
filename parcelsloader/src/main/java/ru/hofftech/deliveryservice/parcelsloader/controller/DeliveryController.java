package ru.hofftech.deliveryservice.parcelsloader.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.response.DeliveryResponseDto;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.LoadCommandProcessingService;
import ru.hofftech.deliveryservice.parcelsloader.service.unloading.UnloadCommandProcessingService;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.request.LoadTrucksCommandDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.request.UnloadTrucksCommandDto;

@RestController
@Tag(name = "DeliveryController", description = "Контроллер для работы с доставками")
@RequestMapping(path = "/api/v1/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DeliveryController {

    private final UnloadCommandProcessingService unloadCommandProcessingService;
    private final LoadCommandProcessingService loadCommandProcessingService;

    @Validated
    @Operation(
            summary = "Погрузить посылки",
            description = "Грузит посылки в грузовики")
    @PostMapping("/load")
    public DeliveryResponseDto load(@RequestBody LoadTrucksCommandDto dto) {
        return loadCommandProcessingService.load(dto);
    }

    @PostMapping("/unload")
    public DeliveryResponseDto unload(@RequestBody UnloadTrucksCommandDto dto) {
        return unloadCommandProcessingService.unload(dto);
    }
}
