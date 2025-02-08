package ru.hofftech.parcelsloader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.parcelsloader.handler.FindBillingHandler;

@RestController
@Tag(name = "BillingController", description = "Операции, связанные с биллингом")
@RequestMapping(path = "/api/v1/billing", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BillingController {

    private final FindBillingHandler findBillingHandler;

    @GetMapping("/{user}")
    public String findByUser(@PathVariable String user) {
        return findBillingHandler.executeCommand(user);
    }
}
