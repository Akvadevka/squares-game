package com.game.controller;

import com.game.dto.*;
import com.game.engine.Board;
import com.game.engine.Move;
import com.game.service.GameService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/{rules}")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    private void checkRules(String rules) {
        if (!"squares".equalsIgnoreCase(rules)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported game rules. Only 'squares' is supported."
            );
        }
    }


    @PostMapping("/nextMove")
    public SimpleMoveDto getNextMove(
            @PathVariable String rules,
            @RequestBody BoardDto boardDto
    ) {
        checkRules(rules);

        try {
            Board board = gameService.createBoardFromDto(boardDto);
            Move move = gameService.findNextMove(board, boardDto.getNextPlayerColor());

            if (move == null) {
                return new SimpleMoveDto(-1, -1, boardDto.getNextPlayerColor());
            }

            return new SimpleMoveDto(
                    move.getX(),
                    move.getY(),
                    move.getPlayerColor().getDisplayValue().toLowerCase()
            );

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PostMapping("/gameStatus")
    public GameStatusDto getGameStatus(
            @PathVariable String rules,
            @RequestBody BoardDto boardDto
    ) {
        checkRules(rules);

        try {
            Board board = gameService.createBoardFromDto(boardDto);
            return gameService.checkStatus(board);

        } catch (IllegalArgumentException e) {
            return new GameStatusDto(-1, "", e.getMessage());
        }
    }
}