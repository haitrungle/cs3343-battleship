package cs3343.battleship.game;

import java.util.Random;

import cs3343.battleship.logic.ship.*;

/**
 * This class contains the configuration of the game.
 */
public final class Config {
    /**
     * The default size of the board.
     */
    public static int BOARD_SIZE = 10;
    /**
     * The default port that the server will listen on.
     */
    public static int DEFAULT_PORT = 1234;
    /**
     * The default seed for the random number generator.
     */
    public static int RANDOM_SEED = 5678;
    /**
     * Whether to enable the typewriter effect (disabled for testing, for example).
     */
    public static boolean TYPEWRITER_EFFECT = true;
    /**
     * Whether to enable color in the console.
     */
    public static boolean CONSOLE_COLOR = false;

    /**
     * Returns a new default fleet of ships: five ships of different types.
     */
    public static final Ship[] defaultFleet() {
        return new Ship[] {
                new AircraftCarrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer()
        };
    }

    /**
     * Returns a new random number generator with the default seed. Calling this
     * method multiple times will return a fresh random number generator each time,
     * guaranteeing the same sequence of random numbers.
     */
    public static Random rng() {
        return new Random(RANDOM_SEED);
    }
}
