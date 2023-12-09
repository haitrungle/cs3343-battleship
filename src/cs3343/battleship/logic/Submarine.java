package cs3343.battleship.logic;

import cs3343.battleship.logic.Position;

public class Submarine extends Ship {
    public Submarine() {
        super("Submarine", 3);
    }

    public Submarine(Direction direction, Position startPosition) {
        super("Submarine", 3, direction, startPosition);
    }
}
