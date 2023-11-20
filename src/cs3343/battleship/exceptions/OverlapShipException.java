package cs3343.battleship.exceptions;

import cs3343.battleship.logic.Position;

public class OverlapShipException extends InvalidInputException {
    public OverlapShipException(Position pos) {
        super("There is an overlapping ship at " + pos + ". Please choose another location for your ship.");
    }
}
