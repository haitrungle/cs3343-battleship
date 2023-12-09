package cs3343.battleship.logic;

public class Battleship extends Ship {
    public Battleship() {
        super("Battleship", 4);
    }

    public Battleship(Direction direction, Position startPosition) {
        super("Battleship", 4, direction, startPosition);
    }
}
