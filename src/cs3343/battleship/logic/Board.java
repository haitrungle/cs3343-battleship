package cs3343.battleship.logic;

import java.util.Arrays;

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
		int row = s.startPosition.row;
		int col = s.startPosition.col;
		if (s.direction == Direction.RIGHT) {
			for (int j = col; j < col + s.length; j++) {
				if (board[row][j] == State.WATER) {
					board[row][j] = State.SHIP;
				} else
					System.out.println("A ship already overlapped");
			}
		} else {
			for (int i = row; i < row + s.length; i++) {
				if (board[i][col] == State.WATER) {
					board[i][col] = State.SHIP;
				} else
					System.out.println("A ship already overlapped");
			}
		}
	}

	public boolean addShot(Position shot) {
		int row = shot.row;
		int col = shot.col;
		switch (board[row][col]) {
			case HIT:
			case MISS:
				System.out.println("Position has already been shot");
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
