package com.game;
import com.game.engine.Board;
import com.game.engine.CellState;
import com.game.engine.Player;
import com.game.engine.CompPlayer;
import com.game.engine.UserPlayer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test");

        try {
            int boardSize = 4;
            Board board = new Board(boardSize);
            System.out.println("Board created with size: " + board.getSize());

            Player comp1 = new CompPlayer(CellState.WHITE);
            Player user1 = new UserPlayer(CellState.BLACK);

            System.out.println("Players created: " + comp1.getType() + " " + comp1.getColor() + ", "
                    + user1.getType() + " " + user1.getColor());

        } catch (Exception e) {
            System.err.println("ERROR");
        }
    }
}