package cs3343.battleship.exceptions;

public class InvalidInputException extends GameException {
    public InvalidInputException(String msg) {
        super("Invalid input: " + msg);
    }
}
