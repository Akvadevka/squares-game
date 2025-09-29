package com.game.engine;

public class SquareChecker {
    public static boolean hasWon(Board board, int lastX, int lastY, CellState color) {
        int N = board.getSize();

        for (int xA = 0; xA < N; xA++) {
            for (int yA = 0; yA < N; yA++) {
                if (xA == lastX && yA == lastY || board.getCellState(xA, yA) != color) {
                    continue;
                }

                int dx = lastX - xA;
                int dy = lastY - yA;


                int xB1 = lastX + dy;
                int yB1 = lastY - dx;
                int xD1 = xA + dy;
                int yD1 = yA - dx;

                if (isSquare(board, xD1, yD1, xB1, yB1, color)) {
                    return true;
                }

                int xB2 = lastX - dy;
                int yB2 = lastY + dx;
                int xD2 = xA - dy;
                int yD2 = yA + dx;

                if (isSquare(board, xD2, yD2, xB2, yB2, color)) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * Вспомогательный метод: проверяет, находятся ли точки B и D
     * на доске и имеют ли они указанный цвет.
     */
    private static boolean isSquare(Board board, int x1, int y1, int x2, int y2, CellState color) {

        if (!board.isValidCoordinate(x1, y1) || !board.isValidCoordinate(x2, y2)) {
            return false;
        }

        return board.getCellState(x1, y1) == color &&
                board.getCellState(x2, y2) == color;
    }
}
