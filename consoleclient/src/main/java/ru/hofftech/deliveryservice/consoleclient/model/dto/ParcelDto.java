package ru.hofftech.deliveryservice.consoleclient.model.dto;

public record ParcelDto(String name,
                        Character symbol,
                        String form) {

    public String formatAsText() {
        var output = new StringBuilder();
        output.append(String.format("id(name): %s", name))
                .append(System.lineSeparator())
                .append("form:")
                .append(System.lineSeparator())
                .append(form);
        return output.toString();
    }
}
