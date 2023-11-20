package cs3343.battleship.exceptions;

public abstract class GameException extends Exception {
    public GameException(String msg) {
        super(msg);
    }
}
