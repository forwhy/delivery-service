package ru.hofftech.delivery.model.entity;

import lombok.Getter;

@Getter
public class MatrixPosition {
    public static final Integer DEFAULT_START_ROW = 0;
    public static final Integer DEFAULT_START_COLUMN = 0;
    private static final Integer NEXT_ROW_NUMBER_INCREMENT = 1;
    private static final Integer NEXT_COLUMN_NUMBER_INCREMENT = 1;

    private final Integer rowNumber;
    private final Integer columnNumber;

    public MatrixPosition() {
        rowNumber = DEFAULT_START_ROW;
        columnNumber = DEFAULT_START_COLUMN;
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
