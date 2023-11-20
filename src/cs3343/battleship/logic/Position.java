package cs3343.battleship.logic;

import java.io.Serializable;

public class Position implements Serializable {
	public final int row;
	public final int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
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

        Position other = (Position)obj;
        return other.row == row && other.col == col;
    }
}
