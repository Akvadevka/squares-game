package com.game.service.exception;

public class NoMoveAvailableException extends RuntimeException{
    public NoMoveAvailableException(String message) {
        super(message);
    }
}
