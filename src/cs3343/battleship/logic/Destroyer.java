package cs3343.battleship.logic;

/**
 * This class represents a Destroyer in the game, a ship with length 2.
 */
public class Destroyer extends Ship {
    /**
     * Constructs a new Destroyer with the start position and direction unset.
     */
    public Destroyer() {
        super("Destroyer", 2);
    }

    /**
     * Constructs a new Destroyer with the specified start position and direction.
     */
    public Destroyer(Direction direction, Position startPosition) {
        super("Destroyer", 2, direction, startPosition);
    }
}
