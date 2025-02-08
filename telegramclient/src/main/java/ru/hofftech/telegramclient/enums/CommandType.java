package ru.hofftech.telegramclient.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandType {
    CREATE_PARCEL("/create"),
    FIND_PARCEL("/find"),
    EDIT_PARCEL("/edit"),
    DELETE_PARCEL("/delete"),
    LOAD_PARCELS("/load"),
    UNLOAD_PARCELS("/unload"),
    HELP("/help");

    private final String commandKeyWord;
}
