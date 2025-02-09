package ru.hofftech.parcelsloader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.parcelsloader.handler.LoadCommandHandler;
import ru.hofftech.parcelsloader.handler.UnloadCommandHandler;
import ru.hofftech.parcelsloader.model.dto.request.LoadTrucksCommandDto;
import ru.hofftech.parcelsloader.model.dto.request.UnloadTrucksCommandDto;

@RestController
@Tag(name = "DeliveryController", description = "Контроллер для работы с доставками")
@RequestMapping(path = "/api/v1/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DeliveryController {

    private final UnloadCommandHandler unloadCommandHandler;
    private final LoadCommandHandler loadCommandHandler;

    @PostMapping("/loading")
    public String load(@RequestBody LoadTrucksCommandDto dto) {
        return loadCommandHandler.executeCommand(dto);
    }

    @PostMapping("/unloading")
    public String unload(@RequestBody UnloadTrucksCommandDto dto) {
        return unloadCommandHandler.executeCommand(dto);
    }
}
