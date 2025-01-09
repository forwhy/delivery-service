package ru.hofftech.delivery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlacedParcelDto implements Serializable {

    private Integer volume;
    private Integer number;
    private Integer startRow;
    private Integer startColumn;
}
