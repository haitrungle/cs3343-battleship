package cs3343.battleship.exceptions;

import cs3343.battleship.logic.Position;

public class PositionShotTwiceException extends InvalidInputException {
    public PositionShotTwiceException(Position pos) {
        super("You have already shot at " + pos + ". Please select another position.");
    }
}
