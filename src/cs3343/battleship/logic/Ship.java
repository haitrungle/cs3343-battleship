package cs3343.battleship.logic;

import java.util.Arrays;
import java.util.List;

public class Ship {
    public final String name;
    public final int length;
    public Position startPosition;
    public Direction direction;

    public Ship(String name, int length, Position startPosition, Direction direction) {
        this.name = name;
        this.length = length;
        this.startPosition = startPosition;
        this.direction = direction;
    }

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

    public static Position overlapPosition(Ship a, Ship b) {
        List<Position> aPositions = a.positions();
        for (Position pb : b.positions()) {
            if (aPositions.contains(pb)) return pb;
        }
        return null;
    }
}
