package ru.hofftech.delivery.model.entity;

import lombok.Getter;
import ru.hofftech.delivery.model.DefaultValues;

@Getter
public class MatrixPosition {

    private static final Integer NEXT_ROW_NUMBER_INCREMENT = 1;
    private static final Integer NEXT_COLUMN_NUMBER_INCREMENT = 1;

    private final Integer rowNumber;
    private final Integer columnNumber;

    public MatrixPosition() {
        rowNumber = DefaultValues.DEFAULT_START_ROW;
        columnNumber = DefaultValues.DEFAULT_START_COLUMN;
    }

    public MatrixPosition(Integer rowNumber, Integer columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public Integer getNextRowNumber() {
        return rowNumber + NEXT_ROW_NUMBER_INCREMENT;
    }

    public Integer getPreviousRowNumber() {
        return rowNumber - NEXT_ROW_NUMBER_INCREMENT;
    }

    public Integer getNextColumnNumber() {
        return columnNumber + NEXT_COLUMN_NUMBER_INCREMENT;
    }
}
