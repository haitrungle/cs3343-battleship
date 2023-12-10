package cs3343.battleship.exceptions;

/**
 * This class represents an exception that occurs while using a Backend.
 */
public class BackendException extends GameException {
    public BackendException(String msg) {
        super("Backend encounter a failure. " + msg);
    }
}
