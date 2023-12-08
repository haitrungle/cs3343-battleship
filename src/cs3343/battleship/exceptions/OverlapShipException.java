package cs3343.battleship.exceptions;

import cs3343.battleship.logic.Position;

/**
 * This exception is thrown when a ship is placed on top of another ship.
 */
public class OverlapShipException extends InvalidInputException {
    public OverlapShipException(Position pos) {
        super("There is an overlapping ship at " + pos + ". Please choose another location for your ship.");
    }
}
