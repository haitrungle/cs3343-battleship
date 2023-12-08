package cs3343.battleship.exceptions;

/**
 * This class is just an alternative for NullPointerException.
 */
public class NullObjectException extends GameException {
    public NullObjectException(Class<?> c) {
        super("An object of class " + c.getName() + " is null");
    }
}
