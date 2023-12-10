package cs3343.battleship.logic;

/**
 * This class represents a Submarine in the game, a ship with length 4.
 */
public class Submarine extends Ship {
    /**
     * Constructs a new Submarine with the start position and direction unset.
     */
    public Submarine() {
        super("Submarine", 3);
    }

    /**
     * Constructs a new Submarine with the specified start position and direction.
     */
    public Submarine(Direction direction, Position startPosition) {
        super("Submarine", 3, direction, startPosition);
    }
}
