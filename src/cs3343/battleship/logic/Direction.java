package cs3343.battleship.logic;

import cs3343.battleship.exceptions.*;

public enum Direction {
	DOWN,
	RIGHT;

	public static Direction decode(String s) throws ValidationException {
		String lower = s.toLowerCase();
		if (lower.equals("d") || lower.equals("down"))
			return DOWN;
		else if (lower.equals("r") || lower.equals("r"))
			return RIGHT;
		else
			throw new ValidationException(s, "Direction");
	}
}
