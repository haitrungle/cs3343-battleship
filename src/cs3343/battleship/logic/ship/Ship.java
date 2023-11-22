package cs3343.battleship.logic.ship;

import java.util.Arrays;
import java.util.List;

import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;

public class Ship {
    private final String name;
    private final int length;
    private Position startPosition;
    private Direction direction;

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public Ship(String name, int length, Direction direction, Position startPosition) {
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

    public String introduce() {
        return name + " (length " + length + ")";
    }

    public static Position overlapPosition(Ship a, Ship b) {
        List<Position> aPositions = a.positions();
        for (Position pb : b.positions()) {
            if (aPositions.contains(pb)) return pb;
        }
        return null;
    }
}
