package ru.hofftech.parcelsloader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.parcelsloader.handler.CreateCommandHandler;
import ru.hofftech.parcelsloader.handler.DeleteCommandHandler;
import ru.hofftech.parcelsloader.handler.EditCommandHandler;
import ru.hofftech.parcelsloader.handler.FindAllCommandHandler;
import ru.hofftech.parcelsloader.handler.FindCommandHandler;
import ru.hofftech.parcelsloader.model.dto.request.CreateParcelCommandDto;
import ru.hofftech.parcelsloader.model.dto.request.EditParcelCommandDto;
import ru.hofftech.parcelsloader.model.dto.request.FindAllParcelCommandDto;

import java.util.Optional;

@RestController
@Tag(name = "ParcelController", description = "Контроллер для работы с посылками")
@RequestMapping(path = "/api/v1/parcels", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ParcelController {

    private final CreateCommandHandler createCommandHandler;
    private final DeleteCommandHandler deleteCommandHandler;
    private final EditCommandHandler editCommandHandler;
    private final FindCommandHandler findCommandHandler;
    private final FindAllCommandHandler findAllCommandHandler;

    @GetMapping("/{parcelId}")
    public String find(@PathVariable String parcelId) {
        return findCommandHandler.executeCommand(parcelId);
    }

    @GetMapping()
    public String findAll(@RequestParam Optional<Long> limit,
                          @RequestParam Optional<Long> offset) {
        return findAllCommandHandler.executeCommand(new FindAllParcelCommandDto(limit, offset));
    }

    @PostMapping()
    public String create(@RequestBody CreateParcelCommandDto dto) {
        return createCommandHandler.executeCommand(dto);
    }

    @PutMapping("/{parcelId}")
    public String update(@PathVariable String parcelId,
                         @RequestBody CreateParcelCommandDto dto) {
        return editCommandHandler.executeCommand(
                new EditParcelCommandDto(parcelId, dto.name(), dto.form(), dto.symbol()));
    }

    @DeleteMapping("/{parcelId}")
    public String delete(@PathVariable String parcelId) {
        return deleteCommandHandler.executeCommand(parcelId);
    }
}
