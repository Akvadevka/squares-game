package com.game.console;

import com.game.engine.*;
import java.util.Scanner;

public class ConsoleApp {

    private static final GameEngine engine = new GameEngine();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Squares Game Console Ready. Enter HELP for commands.");

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String response = processCommand(line);
            if (!response.isEmpty()) {
                System.out.println(response);
            }
            while (engine.isGameActive()) {
                Player currentPlayer = engine.getPlayers().get(engine.getCurrentPlayerColor());

                if (currentPlayer.getType() == PlayerType.COMP) {
                    executeComputerTurn(currentPlayer);
                    if (!engine.isGameActive()) {
                        break;
                    } } else {
                    break;
                }
            }
        }
    }

    private static void executeComputerTurn(Player currentPlayer) {

        Move compMove = engine.executeComputerMove();

        if (compMove != null) {
            String moveOutput = currentPlayer.getColor().getDisplayValue() +
                    " (" + compMove.getX() + ", " + compMove.getY() + ")";
            System.out.println(moveOutput);
        }

        if (engine.printBoard() != null) System.out.println(engine.printBoard());

        if (!engine.isGameActive()) {
            System.out.println(engine.getGameStatus());
        }
    }

    private static String processCommand(String commandLine) {
        String[] parts = commandLine.toUpperCase().split(",");
        String firstPart = parts[0].trim();

        String command;
        int spaceIndex = firstPart.indexOf(' ');

        if (spaceIndex > 0) {
            command = firstPart.substring(0, spaceIndex);
        } else {
            command = firstPart;
        }

        try {
            switch (command) {
                case "GAME":

                    if (parts.length != 3) return "Incorrect command";

                    int size = Integer.parseInt(parts[0].substring(4).trim());
                    Player p1 = parsePlayer(parts[1].trim());
                    Player p2 = parsePlayer(parts[2].trim());

                    engine.startNewGame(size, p1, p2);

                    return "New game started!";

                case "MOVE":
                    if (parts.length != 2) return "Incorrect command";

                    int x = Integer.parseInt(parts[0].substring(4).trim());
                    int y = Integer.parseInt(parts[1].trim());

                    if (engine.getCurrentPlayerColor() != CellState.EMPTY && engine.getPlayers().get(engine.getCurrentPlayerColor()).getType() != PlayerType.USER) {
                        return "ERROR: The computer is running now.";
                    }

                    String result = engine.executeMove(x, y);
                    if (result == null) {
                        return "";
                    }
                    System.out.println(engine.printBoard());

                    return result;

                case "BOARD":
                    return engine.printBoard();

                case "EXIT":
                    System.exit(0);
                    return "";

                case "HELP":
                    return """
                            Commands:
                              GAME N, TYPE1 COLOR1, TYPE2 COLOR2 - Start new game (N=size, TYPE=user/comp, COLOR=W/B)
                              MOVE X, Y - Make a move (0-indexed)
                              BOARD - Print current board state
                              EXIT - Close the game""";

                default:
                    return "Incorrect command. Enter HELP.";
            }
        } catch (NumberFormatException e) {
            return "Incorrect command";
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    private static Player parsePlayer(String playerPart) {
        String[] details = playerPart.split(" ");
        if (details.length != 2) throw new IllegalArgumentException("Incorrect command");

        PlayerType type = PlayerType.valueOf(details[0].toUpperCase());
        String colorSymbol = details[1].toUpperCase();

        CellState color;

        if (colorSymbol.equals("B")) {
            color = CellState.BLACK;
        } else if (colorSymbol.equals("W")) {
            color = CellState.WHITE;
        } else {
            throw new IllegalArgumentException("Incorrect command");
        }

        if (type == PlayerType.USER) {
            return new UserPlayer(color);
        } else {
            return new CompPlayer(color);
        }
    }
}