package com.game.engine;

public class UserPlayer extends Player {

    public UserPlayer(CellState color) {
        super(PlayerType.USER, color);
    }

    @Override
    public Move getMove(Board board) {
        return null;
    }
}