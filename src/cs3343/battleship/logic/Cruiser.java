package cs3343.battleship.logic;

/**
 * This class represents a Cruiser in the game, a ship with length 3.
 */
public class Cruiser extends Ship {
    /**
     * Constructs a new Cruiser with the start position and direction unset.
     */
    public Cruiser() {
        super("Cruiser", 3);
    }

    /**
     * Constructs a new Cruiser with the specified start position and direction.
     */
    public Cruiser(Direction direction, Position startPosition) {
        super("Cruiser", 3, direction, startPosition);
    }
}
