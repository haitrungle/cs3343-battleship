package cs3343.battleship.logic;

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
}
