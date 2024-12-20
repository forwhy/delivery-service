package ru.hofftech.delivery.model.entity;

import lombok.Getter;

@Getter
public class MatrixPosition {
    public static final int DEFAULT_START_ROW = 0;
    public static final int DEFAULT_START_COLUMN = 0;
    private static final int NEXT_ROW_NUMBER_INCREMENT = 1;
    private static final int NEXT_COLUMN_NUMBER_INCREMENT = 1;

    private int rowNumber;
    private int columnNumber;

    public MatrixPosition() {
        rowNumber = DEFAULT_START_ROW;
        columnNumber = DEFAULT_START_COLUMN;
    }

    public MatrixPosition(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public int getNextRowNumber() {
        return rowNumber + NEXT_ROW_NUMBER_INCREMENT;
    }

    public int getPreviousRowNumber() {
        return rowNumber - NEXT_ROW_NUMBER_INCREMENT;
    }

    public int getNextColumnNumber() {
        return columnNumber + NEXT_COLUMN_NUMBER_INCREMENT;
    }
}
