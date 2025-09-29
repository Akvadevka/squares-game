package com.game.engine;

public enum PlayerType {
    USER("user"),
    COMP("comp");

    private final String displayValue;

    PlayerType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}