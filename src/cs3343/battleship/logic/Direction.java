package cs3343.battleship.logic;

import java.util.Random;

import cs3343.battleship.exceptions.InvalidInputException;

/**
 * This enum represents a direction in which a ship can be placed.
 * 
 * Possible values are DOWN and RIGHT, corresponding to the vertical and
 * horizontal directions from the start position as origin, respectively, but
 * more descriptive and shorter. We could have included UP and LEFT as well,
 * but they are redundant as they are just the opposites of the other two.
 */
public enum Direction {
    /**
     * The vertical direction from the start position as origin.
     */
    DOWN,
    /**
     * The horizontal direction from the start position as origin.
     */
    RIGHT;

    /**
     * Returns the direction corresponding to the given string.
     * 
     * @param s string representing the direction
     * @return the corresponding direction
     * @throws InvalidInputException if the string is not a valid direction
     */
    public static Direction decode(String s) throws InvalidInputException {
        String lower = s.toLowerCase();
        if (lower.equals("d") || lower.equals("down"))
            return DOWN;
        else if (lower.equals("r") || lower.equals("right"))
            return RIGHT;
        else
            throw new InvalidInputException("Direction can only be 'd'/'down' or 'r'/'right'.");
    }

    /**
     * Returns a random direction from the given random number generator.
     * 
     * Note that this changes the state of the input RNG.
     * 
     * @param rng the random number generator
     * @return a random direction
     */
    public static Direction random(Random rng) {
        return rng.nextBoolean() ? DOWN : RIGHT;
    }
}