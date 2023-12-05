package cs3343.battleship.exceptions;

public class NullObjectException extends GameException {
    public NullObjectException(Class<?> c) {
        super("An object of class " + c.getName() + " is null");
    }
}
