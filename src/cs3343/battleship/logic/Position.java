package cs3343.battleship.logic;

import java.io.Serializable;
import java.util.Random;

public class Position implements Serializable {
    public final int row;
    public final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

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
