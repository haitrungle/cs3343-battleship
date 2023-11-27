package cs3343.battleship.logic;

import java.util.Random;

import cs3343.battleship.exceptions.*;

public enum Direction {
	DOWN,
	RIGHT;

	public static Direction decode(String s) throws InvalidInputException {
		String lower = s.toLowerCase();
		if (lower.equals("d") || lower.equals("down"))
			return DOWN;
		else if (lower.equals("r") || lower.equals("r"))
			return RIGHT;
		else
			throw new InvalidInputException("Direction can only be 'd'/'down' or 'r'/'right'.");
	}

	public static Direction random(Random rng) {
		return rng.nextBoolean() ? DOWN : RIGHT;
	}
}
