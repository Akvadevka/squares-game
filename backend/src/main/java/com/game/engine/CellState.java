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

    public static CellState fromSymbol(String symbol) {
        if (symbol == null || symbol.length() != 1) {
            throw new IllegalArgumentException("Symbol must be a single non-null character.");
        }

        char charSymbol = Character.toUpperCase(symbol.charAt(0));

        return switch (charSymbol) {
            case 'B' -> BLACK;
            case 'W' -> WHITE;
            case ' ', '.' -> EMPTY;
            default -> throw new IllegalArgumentException("Incorrect command");
        };
    }
}
