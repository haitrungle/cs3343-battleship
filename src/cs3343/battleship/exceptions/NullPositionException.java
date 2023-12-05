package cs3343.battleship.exceptions;

import cs3343.battleship.logic.ship.Ship;

public class NullPositionException extends GameException {
    public NullPositionException(Ship s) {
        super("A ship with name " + s.getName() + " has null start position");
    }
}
