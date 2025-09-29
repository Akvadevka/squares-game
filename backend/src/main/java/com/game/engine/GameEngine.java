package com.game.engine;

import java.util.HashMap;
import java.util.Map;

public class GameEngine {

    private Board board;
    private Map<CellState, Player> players;
    private CellState currentPlayerColor;
    private boolean isGameActive;
    private String gameStatus;

    public GameEngine() {
        this.isGameActive = false;
        this.gameStatus = "READY";
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public CellState getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    public Map<CellState, Player> getPlayers() {
        return players;
    }

    public void startNewGame(int size, Player player1, Player player2) {
        this.board = new Board(size);
        this.players = new HashMap<>();

        if (player1.getColor() == player2.getColor() || player1.getColor() == CellState.EMPTY) {
            throw new IllegalArgumentException("The players must have different colors (WHITE/BLACK).");
        }

        players.put(player1.getColor(), player1);
        players.put(player2.getColor(), player2);

        this.currentPlayerColor = player1.getColor();

        this.isGameActive = true;
        this.gameStatus = "ACTIVE";
    }

    /**
     * Выполняет ход. Используется для хода человека или результата хода компьютера.
     * @param x Координата X.
     * @param y Координата Y.
     */
    public String executeMove(int x, int y) {
        if (!isGameActive) {
            return "ERROR: Game not active.";
        }

        CellState color = currentPlayerColor;

        try {
            board.placePiece(x, y, color);

            if (SquareChecker.hasWon(board, x, y, color)) {
                isGameActive = false;
                gameStatus = "Game finished. " + color.getDisplayValue().toUpperCase() + " wins!";
                return gameStatus;
            }

            if (board.isFull()) {
                isGameActive = false;
                gameStatus = "Game finished. Draw";
                return gameStatus;
            }

            switchPlayer();
            return null;

        } catch (IllegalArgumentException | IllegalStateException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Вычисляет и выполняет ход для игрока-компьютера.
     */
    public Move executeComputerMove() {
        Player computer = players.get(currentPlayerColor);
        Move compMove = computer.getMove(board);

        if (compMove == null) {
            isGameActive = false;
            gameStatus = "Game finished. Draw";
            return null;
        }

        executeMove(compMove.getX(), compMove.getY());
        return compMove;
    }

    private void switchPlayer() {
        if (currentPlayerColor == CellState.BLACK) {
            currentPlayerColor = CellState.WHITE;
        } else {
            currentPlayerColor = CellState.BLACK;
        }
    }

    public String printBoard() {
        if (board == null) return "The board not initialised.";
        return board.toString();
    }
}
