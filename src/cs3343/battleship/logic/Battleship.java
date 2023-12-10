package cs3343.battleship.logic;

/**
 * This class represents a Battleship in the game, a ship with length 4.
 */
public class Battleship extends Ship {
    /**
     * Constructs a new Battleship with the start position and direction unset.
     */
    public Battleship() {
        super("Battleship", 4);
    }

    /**
     * Constructs a new Battleship with the specified start position and direction.
     */
    public Battleship(Direction direction, Position startPosition) {
        super("Battleship", 4, direction, startPosition);
    }
}
