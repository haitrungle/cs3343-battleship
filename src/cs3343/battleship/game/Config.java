package cs3343.battleship.game;

import java.util.Random;

import cs3343.battleship.logic.AircraftCarrier;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Cruiser;
import cs3343.battleship.logic.Destroyer;
import cs3343.battleship.logic.Ship;
import cs3343.battleship.logic.Submarine;

/**
 * This class contains the configuration of the game. To set custom config
 * values, use the {@link Setter} class. Otherwise, the default values will be
 * used.
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
     * Get the board size.
     * 
     * @return the board size
     */
    public static int getBoardSize() {
        return boardSize;
    }

    /**
     * Get the server port.
     * 
     * @return the server port
     */
    public static int getServerPort() {
        return serverPort;
    }

    /**
     * Get the delay of the typewriter effect.
     * 
     * @return the delay in millisecond
     */
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

    /**
     * Sets the configuration to the default values.
     */
    public static void reset() {
        Config.boardSize = DEFAULT_BOARD_SIZE;
        Config.serverPort = DEFAULT_SERVER_PORT;
        Config.randomSeed = DEFAULT_RANDOM_SEED;
        Config.typewriterDelay = DEFAULT_TYPEWRITER_DELAY;
    }

    /**
     * Sets the configuration to the specified values.
     * 
     * @param boardSize       the board size
     * @param serverPort      the server port
     * @param randomSeed      the random seed
     * @param typewriterDelay the typewriter effect delay
     */
    private static void setConfig(int boardSize, int serverPort, int randomSeed, int typewriterDelay) {
        Config.boardSize = boardSize;
        Config.serverPort = serverPort;
        Config.randomSeed = randomSeed;
        Config.typewriterDelay = typewriterDelay;
    }

    /**
     * This class implements the builder pattern for setting the configuration. To
     * set the configuration, first construct an instance of this class, then call
     * the
     * the appropriate methods, and finally call {@link Setter#set()}. If you don't
     * do
     * this, the configuration will be set to the default values.
     */
    public static class Setter {
        private int boardSize = DEFAULT_BOARD_SIZE;
        private int serverPort = DEFAULT_SERVER_PORT;
        private int randomSeed = DEFAULT_RANDOM_SEED;
        private int typewriterDelay = DEFAULT_RANDOM_SEED;

        /**
         * Constructs a new Setter.
         */
        public Setter() {
        }

        /**
         * Sets the board size to the given value.
         * 
         * @param boardSize the board size
         * @return this Setter
         */
        public Setter withBoardSize(int boardSize) {
            this.boardSize = boardSize;
            return this;
        }

        /**
         * Sets the server port to the given value.
         * 
         * @param serverPort the server port
         * @return this Setter
         */
        public Setter withServerPort(int serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        /**
         * Sets the random seed to the given value
         * 
         * @param randomSeed the random seed
         * @return this Setter
         */
        public Setter withRandomSeed(int randomSeed) {
            this.randomSeed = randomSeed;
            return this;
        }

        /**
         * Sets the random seed to the given value
         * 
         * @param typewriterDelay the delay for typewriter effect
         * @return this Setter
         */
        public Setter withTypewriterDelay(int typewriterDelay) {
            this.typewriterDelay = typewriterDelay;
            return this;
        }

        /**
         * Sets the configuration to the values given to this Setter.
         */
        public void set() {
            Config.setConfig(boardSize, serverPort, randomSeed, typewriterDelay);
        }
    }
}
