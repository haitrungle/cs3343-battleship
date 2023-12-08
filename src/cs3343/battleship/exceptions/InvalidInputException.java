package cs3343.battleship.exceptions;

import cs3343.battleship.game.Console;

/**
 * This class is thrown when the user enters an invalid input. This is rather
 * generic, so use a specific subclass of this if possible.
 */
public class InvalidInputException extends GameException {
    public InvalidInputException(String msg) {
        super(Console.colorize("Invalid input: ", Console.Color.RED) + msg);
    }
}
