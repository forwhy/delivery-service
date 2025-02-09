package ru.hofftech.telegramclient.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandType {
    CREATE_PARCEL("/create"),
    FIND_PARCEL("/find"),
    FIND_ALL_PARCELS("/find-all"),
    EDIT_PARCEL("/edit"),
    DELETE_PARCEL("/delete"),
    LOAD_PARCELS("/load"),
    UNLOAD_PARCELS("/unload"),
    HELP("/help");

    private final String commandKeyWord;
}
