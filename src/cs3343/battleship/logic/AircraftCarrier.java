package cs3343.battleship.logic;

/**
 * This class represents an Aircraft Carrier in the game, a ship with length 5.
 */
public class AircraftCarrier extends Ship {
    /**
     * Constructs a new Aircraft Carrier with the start position and direction unset.
     */
    public AircraftCarrier() {
        super("Aircraft Carrier", 5);
    }

    /**
     * Constructs a new Aircraft Carrier with the specified start position and direction.
     */
    public AircraftCarrier(Direction direction, Position startPosition) {
        super("Aircraft Carrier", 5, direction, startPosition);
    }
}
