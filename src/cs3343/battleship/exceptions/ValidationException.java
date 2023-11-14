package cs3343.battleship.exceptions;

public class ValidationException extends GameException {
    public ValidationException(String source, String obj) {
        super(String.format("Cannot parse '%s' into an object of type `%s`", source, obj));
    }
}
