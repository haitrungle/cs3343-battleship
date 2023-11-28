package cs3343.battleship.game;

import java.util.Random;

import cs3343.battleship.logic.ship.*;

public final class Config {
    public static int BOARD_SIZE = 10;
    public static int DEFAULT_PORT = 1234;
    public static Ship[] defaultFleet() {
        return new Ship[] {
                new AircraftCarrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer()
        };
    }
    public static Random rng = new Random(1234);
}
