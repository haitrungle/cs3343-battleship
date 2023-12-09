package cs3343.battleship.logic;

public class Cruiser extends Ship {
    public Cruiser() {
        super("Cruiser", 3);
    }

    public Cruiser(Direction direction, Position startPosition) {
        super("Cruiser", 3, direction, startPosition);
    }
}
