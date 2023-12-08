package cs3343.battleship.exceptions;

import cs3343.battleship.logic.Position;

/**
 * This exception is thrown when the user shoots at a position that has already been shot at.
 */
public class PositionShotTwiceException extends InvalidInputException {
    public PositionShotTwiceException(Position pos) {
        super("You have already shot at " + pos + ". Please select another position.");
    }
}
