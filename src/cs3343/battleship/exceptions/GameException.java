package cs3343.battleship.exceptions;

/**
 * This class represents an exception that occurs during the game.
 */
public abstract class GameException extends Exception {
    public GameException(String msg) {
        super(msg);
    }
}
