package cs3343.battleship.game;

import java.util.Random;

import cs3343.battleship.logic.ship.*;

public final class Config {
    public static final int BOARD_SIZE = 10;
    public static final int DEFAULT_PORT = 1234;
    public static final int RANDOM_SEED = 5678;

    public static final Ship[] defaultFleet() {
        return new Ship[] {
                new AircraftCarrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer()
        };
    }
    public static Random rng() {
        return new Random(RANDOM_SEED);
    }
}
