package ru.hofftech.deliveryservice.telegramclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.deliveryservice.telegramclient.enums.CommandType;
import ru.hofftech.deliveryservice.telegramclient.model.Command;
import ru.hofftech.deliveryservice.telegramclient.model.impl.*;
import ru.hofftech.deliveryservice.telegramclient.service.CommandProcessorService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandConfig {

    @Bean
    public CommandProcessorService commandProcessorService(
            CreateParcelCommand createParcelCommand,
            DeleteParcelCommand deleteParcelCommand,
            EditParcelCommand editParcelCommand,
            FindAllParcelCommand findAllParcelCommand,
            FindBillingsByUserCommand findBillingsByUserCommand,
            FindParcelCommand findParcelCommand,
            HelpCommand helpCommand,
            LoadTrucksCommand loadTrucksCommand,
            StartCommand startCommand,
            UnloadTrucksCommand unloadTrucksCommand) {
        Map<String, Command> commands = new HashMap<>();
        commands.put(CommandType.START.getCommandKeyWord(), createParcelCommand);
        commands.put(CommandType.CREATE_PARCEL.getCommandKeyWord(), createParcelCommand);
        commands.put(CommandType.DELETE_PARCEL.getCommandKeyWord(), deleteParcelCommand);
        commands.put(CommandType.EDIT_PARCEL.getCommandKeyWord(), editParcelCommand);
        commands.put(CommandType.FIND_ALL_PARCELS.getCommandKeyWord(), findAllParcelCommand);
        commands.put(CommandType.BILLING.getCommandKeyWord(), findBillingsByUserCommand);
        commands.put(CommandType.FIND_PARCEL.getCommandKeyWord(), findParcelCommand);
        commands.put(CommandType.HELP.getCommandKeyWord(), helpCommand);
        commands.put(CommandType.LOAD_PARCELS.getCommandKeyWord(), loadTrucksCommand);
        commands.put(CommandType.START.getCommandKeyWord(), startCommand);
        commands.put(CommandType.UNLOAD_PARCELS.getCommandKeyWord(), unloadTrucksCommand);

        return new CommandProcessorService(commands);
    }
}
