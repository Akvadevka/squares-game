package com.game.engine;

public abstract class Player {
    private final PlayerType type;
    private final CellState color;

    public Player(PlayerType type, CellState color) {
        this.type = type;
        this.color = color;
    }

    public PlayerType getType() {
        return type;
    }

    public CellState getColor() {
        return color;
    }

    public abstract Move getMove(Board board) throws Exception;
}