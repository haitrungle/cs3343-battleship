package cs3343.battleship.logic;

import java.util.Arrays;

import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.logic.ship.Ship;

public final class Board {
	public enum State {
		HIT,
		MISS,
		SHIP,
		WATER;

		@Override
		public String toString() {
			switch (this.ordinal()) {
				case 0:
					return "×";
				case 1:
					return "⋆";
				case 2:
					return "□";
				case 3:
					return "~";
				default:
					return null;
			}
		}
	}

	// Board is a square with width = height = size
	public int size;
	public State[][] board;

	public Board(int size) {
		this.size = size;
		board = initBoard();
	}

	private State[][] initBoard() {
		State[][] table = new State[size][size];
		for (State[] row : table) {
			Arrays.fill(row, State.WATER);
		}
		return table;
	}

	public void addShip(Ship s) {
		int row = s.getStartPosition().row;
		int col = s.getStartPosition().col;
		if (s.getDirection() == Direction.RIGHT) {
			for (int j = col; j < col + s.getLength(); j++) {
				if (board[row][j] == State.WATER) {
					board[row][j] = State.SHIP;
				} else
					System.out.println("A ship already overlapped");
			}
		} else {
			for (int i = row; i < row + s.getLength(); i++) {
				if (board[i][col] == State.WATER) {
					board[i][col] = State.SHIP;
				} else
					System.out.println("A ship already overlapped");
			}
		}
	}

	public boolean addShot(Position shot) throws PositionShotTwiceException {
		int row = shot.row;
		int col = shot.col;
		switch (board[row][col]) {
			case HIT:
			case MISS:
				throw new PositionShotTwiceException(shot);
			case WATER:
				board[row][col] = State.MISS;
				return false;
			case SHIP:
				board[row][col] = State.HIT;
				return true;
			default:
				System.out.println("Unreachable");
		}
		return false;
	}

	public void setState(Position position, State state) {
		board[position.row][position.col] = state;
	}

	public boolean hasAliveShip() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == State.SHIP)
					return true;
			}
		}
		return false;
	}
}
