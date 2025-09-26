package com.game.engine;

import java.util.List;
import java.util.Random;

public class CompPlayer extends Player {

    private final Random random = new Random();

    public CompPlayer(CellState color) {
        super(PlayerType.COMP, color);
    }

    @Override
    public Move getMove(Board board) {
        CellState compColor = this.getColor();
        CellState opponentColor = (compColor == CellState.WHITE) ? CellState.BLACK : CellState.WHITE;

        List<Move> availableMoves = board.getAvailableMoves(compColor);
        if (availableMoves.isEmpty()) {
            return null;
        }

        Move winningMove = findWinningMove(board, availableMoves, compColor);
        if (winningMove != null) {
            return winningMove;
        }

        Move blockingMove = findWinningMove(board, availableMoves, opponentColor);
        if (blockingMove != null) {
            return blockingMove;
        }

        int index = random.nextInt(availableMoves.size());
        return availableMoves.get(index);
    }

    /**
     * Вспомогательный метод: ищет ход, который приведет к немедленной победе
     * для указанного цвета.
     */
    private Move findWinningMove(Board board, List<Move> moves, CellState targetColor) {
        for (Move move : moves) {
            board.placePiece(move.getX(), move.getY(), targetColor);

            boolean wins = SquareChecker.hasWon(
                    board,
                    move.getX(),
                    move.getY(),
                    targetColor
            );

            board.undoMove(move.getX(), move.getY());

            if (wins) {
                if (targetColor == this.getColor()) {
                    return move;
                } else {
                    return new Move(move.getX(), move.getY(), this.getColor());
                }
            }
        }
        return null;
    }
}
