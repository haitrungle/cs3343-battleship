package cs3343.battleship.exceptions;

import cs3343.battleship.game.Console;

public class DirectionIsNotExistException extends Exception {
    public DirectionIsNotExistException(String msg) {
        super(msg);
    }

}
