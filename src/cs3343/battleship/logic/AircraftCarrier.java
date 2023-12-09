package cs3343.battleship.logic;

import cs3343.battleship.logic.Position;

public class AircraftCarrier extends Ship {
    public AircraftCarrier() {
        super("Aircraft Carrier", 5);
    }

    public AircraftCarrier(Direction direction, Position startPosition) {
        super("Aircraft Carrier", 5, direction, startPosition);
    }
}
