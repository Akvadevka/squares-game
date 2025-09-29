package com.game.engine;

public class Move {
    private final int x;
    private final int y;
    private final CellState color;

    public Move(int x, int y, CellState color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellState getPlayerColor() {
        return color;
    }
}