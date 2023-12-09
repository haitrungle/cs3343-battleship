package cs3343.battleship.game;

import java.util.Random;

import cs3343.battleship.logic.AircraftCarrier;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Cruiser;
import cs3343.battleship.logic.Destroyer;
import cs3343.battleship.logic.Ship;
import cs3343.battleship.logic.Submarine;

/**
 * This class contains the configuration of the game.
 */
public abstract class Config {
    /**
     * The default size of the board.
     */
    public static final int DEFAULT_BOARD_SIZE = 10;

    /**
     * The default port that the server will listen on.
     */
    public static final int DEFAULT_SERVER_PORT = 1234;

    /**
     * The default seed for the random number generator.
     */
    public static final int DEFAULT_RANDOM_SEED = 5678;

    /**
     * Typewriter effect delay in milliseconds. If 0, the effect will be disabled.
     */
    public static final int DEFAULT_TYPEWRITER_DELAY = 75;

    private static int boardSize = DEFAULT_BOARD_SIZE;
    private static int serverPort = DEFAULT_SERVER_PORT;
    public static int randomSeed = DEFAULT_RANDOM_SEED;
    public static int typewriterDelay = DEFAULT_TYPEWRITER_DELAY;
    /**
     * Flag to allow setting config only once during the program's lifetime.
     */
    private static boolean hasSet = false;

    public static int getBoardSize() {
        return boardSize;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static int getTypewriterDelay() {
        return typewriterDelay;
    }

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
        return new Random(randomSeed);
    }

    private static void setConfig(int boardSize, int serverPort, int randomSeed, int typewriterDelay) {
        if (hasSet) return;
        Config.boardSize = boardSize;
        Config.serverPort = serverPort;
        Config.randomSeed = randomSeed;
        Config.typewriterDelay = typewriterDelay;
        hasSet = true;
    }

    public static class Setter {
        private int boardSize = DEFAULT_BOARD_SIZE;
        private int serverPort = DEFAULT_SERVER_PORT;
        private int randomSeed = DEFAULT_RANDOM_SEED;
        private int typewriterDelay = DEFAULT_RANDOM_SEED;

        public Setter() {
        }

        public Setter withBoardSize(int boardSize) {
            this.boardSize = boardSize;
            return this;
        }

        public Setter withServerPort(int serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        public Setter withRandomSeed(int randomSeed) {
            this.randomSeed = randomSeed;
            return this;
        }

        public Setter withTypewriterDelay(int typewriterDelay) {
            this.typewriterDelay = typewriterDelay;
            return this;
        }

        public void set() {
            Config.setConfig(boardSize, serverPort, randomSeed, typewriterDelay);
        }
    }
}
