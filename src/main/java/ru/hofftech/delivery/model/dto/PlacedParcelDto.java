package ru.hofftech.delivery.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PlacedParcelDto implements Serializable {
    private Integer volume;
    private Integer number;
    private Integer startRow;
    private Integer startColumn;

    public PlacedParcelDto() {}

    public PlacedParcelDto(Integer volume, Integer number, Integer startRow, Integer startColumn) {
        this.volume = volume;
        this.number = number;
        this.startRow = startRow;
        this.startColumn = startColumn;
    }
}
