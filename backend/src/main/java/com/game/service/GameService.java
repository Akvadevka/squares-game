package com.game.service;
import com.game.dto.BoardDto;
import com.game.dto.GameStatusDto;
import com.game.engine.Board;
import com.game.engine.CellState;
import com.game.engine.CompPlayer;
import com.game.engine.Move;
import com.game.engine.SquareChecker;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GameService {
    public Board createBoardFromDto(BoardDto boardDto) {
        int size = boardDto.getSize();
        Board board = new Board(size);
        String data = boardDto.getData();

        if (data.length() != size * size) {
            throw new IllegalArgumentException("Board data length mismatch.");
        }

        for (int i = 0; i < size * size; i++) {
            int y = i / size;
            int x = i % size;

            String symbol = String.valueOf(data.charAt(i));
            CellState state = CellState.fromSymbol(symbol);

            if (state != CellState.EMPTY) {
                board.placePiece(x, y, state);
            }
        }
        return board;
    }

    public Move findNextMove(Board board, String nextPlayerColorStr) {
        CellState compColor = CellState.fromSymbol(nextPlayerColorStr);
        CompPlayer compPlayer = new CompPlayer(compColor);
        return compPlayer.getMove(board);
    }

    public GameStatusDto checkStatus(Board currentBoard) {

        if (isWinning(currentBoard, CellState.BLACK)) {
            return new GameStatusDto(1, "b", "win");
        }

        if (isWinning(currentBoard, CellState.WHITE)) {
            return new GameStatusDto(1, "w", "win");
        }

        if (currentBoard.isFull()) {
            return new GameStatusDto(2, "", "draw");
        }

        return new GameStatusDto(0, "", "ok");
    }

    private boolean isWinning(Board board, CellState color) {

        List<int[]> playerPieces = board.getOccupiedCells(color);

        for (int[] currentPiece : playerPieces) {
            int x = currentPiece[0];
            int y = currentPiece[1];

            if (SquareChecker.hasWon(board, x, y, color)) {
                return true;
            }
        }
        return false;
    }
}