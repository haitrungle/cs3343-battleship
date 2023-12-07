package cs3343.battleship.logic.ship;

import cs3343.battleship.logic.Position;

public class Battleship extends Ship {
    public Battleship() {
        super("Battleship", 4);
    }

    public Battleship(Direction direction, Position startPosition) {
        super("Battleship", 4, direction, startPosition);
    }
}
