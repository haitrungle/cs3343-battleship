package cs3343.battleship.exceptions;

public class BackendException extends GameException {
    public BackendException(String msg) {
        super("Backend encounter a failure. " + msg);
    }
}
