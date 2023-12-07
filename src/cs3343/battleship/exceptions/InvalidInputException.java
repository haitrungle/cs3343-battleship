package cs3343.battleship.exceptions;

import cs3343.battleship.game.Console;

public class InvalidInputException extends GameException {
    public InvalidInputException(String msg) {
        super(Console.colorize("Invalid input: ", Console.Color.RED) + msg);
    }
}
