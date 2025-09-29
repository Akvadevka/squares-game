package com.game.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int size;
    private final CellState[][] grid;
    private int piecesCount = 0;

    public Board(int size) {
        if (size < 3) {
            throw new IllegalArgumentException("The board size must be greater than 2");
        }
        this.size = size;
        this.grid = new CellState[size][size];

        for (CellState[] row : grid) {
            Arrays.fill(row, CellState.EMPTY);
        }
    }

    public int getSize() {
        return size;
    }

    public CellState getCellState(int x, int y) {
        if (!isValidCoordinate(x, y)) {
            throw new IllegalArgumentException("The coordinates (" + x + ", " + y + ") are located outside the board");
        }
        return grid[x][y];
    }

    public void placePiece(int x, int y, CellState color) {
        if (!isValidCoordinate(x, y)) {
            throw new IllegalArgumentException("Incorrect command");
        }
        if (grid[x][y] != CellState.EMPTY) {
            throw new IllegalStateException("The coordinates (" + x + ", " + y + ") are occupied");
        }

        grid[x][y] = color;
        piecesCount++;
    }

    public boolean isFull() {
        return piecesCount == size * size;
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public void undoMove(int x, int y) {
        if (grid[x][y] != CellState.EMPTY) {
            grid[x][y] = CellState.EMPTY;
            piecesCount--;
        }
    }

    public List<Move> getAvailableMoves(CellState currentColor) {
        List<Move> moves = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[x][y] == CellState.EMPTY) {
                    moves.add(new Move(x, y, currentColor));
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int x = 0; x < size; x++) {
            sb.append(x).append(" ");
        }
        sb.append("\n");

        for (int y = 0; y < size; y++) {
            sb.append(y).append(" ");

            for (int x = 0; x < size; x++) {
                sb.append(grid[x][y].getDisplayValue()).append(" ");
            }

            sb.setLength(sb.length() - 1);
            sb.append("\n");
        }
        return sb.toString();
    }
}