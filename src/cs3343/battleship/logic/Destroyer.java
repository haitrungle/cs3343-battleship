package cs3343.battleship.logic;

import cs3343.battleship.logic.Position;

public class Destroyer extends Ship {
    public Destroyer() {
        super("Destroyer", 2);
    }

    public Destroyer(Direction direction, Position startPosition) {
        super("Destroyer", 2, direction, startPosition);
    }
}
