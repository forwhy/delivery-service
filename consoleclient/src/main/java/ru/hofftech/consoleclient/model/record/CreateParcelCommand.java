package ru.hofftech.consoleclient.model.record;

public record CreateParcelCommand (String name,
                                   String form,
                                   Character symbol) {
}
