package ru.hofftech.delivery.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PlacedParcelDto implements Serializable {
    private Integer volume;
    private Integer number;
    private Integer startRow;
    private Integer startColumn;

    public PlacedParcelDto(Integer volume, Integer number, Integer rowNumber, Integer columnNumber) {
        this.volume = volume;
        this.number = number;
        this.startRow = rowNumber;
        this.startColumn = columnNumber;
    }
}
