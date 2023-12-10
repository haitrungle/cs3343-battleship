package cs3343.battleship.logic;

import java.io.Serializable;
import java.util.Random;

/**
 * An (row, column) coordinate pair representing a position on the board.
 * 
 * Worth noting that the row and column are zero-indexed and that the top-most
 * row is row 0 and the left-most column is column 0.
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 19122003L;

    public final int row;
    public final int col;

    /**
     * Creates a new position.
     * 
     * @param row row number (zero-indexed))
     * @param col column number (zero-indexed)
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns a new random position from the given random number generator.
     * 
     * Note that this changes the state of the input RNG.
     * 
     * @param rng  the random number generator
     * @param size the size of the board
     * @return a new random position
     */
    public static Position random(Random rng, int size) {
        return new Position(rng.nextInt(size), rng.nextInt(size));
    }

    @Override
    public String toString() {
        return row + "," + col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Position.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        Position other = (Position) obj;
        return other.row == row && other.col == col;
    }
}
