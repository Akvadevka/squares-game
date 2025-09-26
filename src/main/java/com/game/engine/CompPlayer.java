package com.game.engine;

public class CompPlayer extends Player {

    public CompPlayer(CellState color) {
        super(PlayerType.COMP, color);
    }

    @Override
    public Move getMove(Board board) {
        return null;
    }
}
