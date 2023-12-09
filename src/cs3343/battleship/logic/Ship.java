package cs3343.battleship.logic;

import java.util.Arrays;
import java.util.List;

/**
 * This abstract class represents a ship in the game.
 * 
 * A ship is located by three components: length, start position, and direction.
 * Notably, the existence of a ship is separate from that of a board: a ship can
 * has any length or position, and only until added to a board does it gets
 * checked for validity. The only requirement imposed by this class is that
 * its length is positive.
 * 
 * Right now, there are no need to create custom ship, so this has been made an
 * abstract class. Instead, you can use the subclasses inheriting from it.
 */
public abstract class Ship {
    private final String name;
    private final int length;
    private Position startPosition;
    private Direction direction;

    /**
     * Returns the name of the ship.
     * 
     * @return name of this ship
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the length of the ship.
     * 
     * @return length of this ship
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the start position of the ship.
     * 
     * @return start position of this ship
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * Set the start position of the ship.
     * 
     * @param startPosition new start position of this ship
     */
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Returns the direction of the ship.
     * 
     * @return direction of this ship
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Set the direction of the ship.
     * 
     * @param direction new direction of this ship
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }

    Ship(String name, int length, Direction direction, Position startPosition) {
        this.name = name;
        this.length = length;
        this.startPosition = startPosition;
        this.direction = direction;
    }

    /**
     * Returns a list of positions occupied by this ship.
     * 
     * @return a list of positions occupied by this ship
     */
    public List<Position> positions() {
        Position[] positions = new Position[length];
        for (int i = 0; i < length; i++) {
            if (direction == Direction.DOWN) {
                positions[i] = new Position(startPosition.row + i, startPosition.col);
            } else {
                positions[i] = new Position(startPosition.row, startPosition.col + i);
            }
        }
        return Arrays.asList(positions);
    }

    /**
     * Returns a string that describes this ship.
     * 
     * @return a string representation of this ship
     */
    public String introduce() {
        return name + " (length " + length + ")";
    }

    /**
     * Returns the position where two ships overlap, or null if they do not overlap.
     * 
     * @param a first ship
     * @param b second ship
     * @return the position where two ships overlap, or null if they do not overlap
     */
    public static Position overlapPosition(Ship a, Ship b) {
        List<Position> aPositions = a.positions();
        for (Position pb : b.positions()) {
            if (aPositions.contains(pb))
                return pb;
        }
        return null;
    }
}
