package com.game.engine;

public enum CellState {
    EMPTY("."),
    BLACK("B"),
    WHITE("W");

    private final String displayValue;

    CellState(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
