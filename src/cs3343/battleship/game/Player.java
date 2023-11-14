package cs3343.battleship.game;

import java.util.ArrayList;
import java.util.List;

import cs3343.battleship.logic.Board;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;
import cs3343.battleship.logic.Board.State;

public final class Player {
	private String name;
	private Board board = new Board(10);
	private Board enemyBoard = new Board(10);
	private List<Ship> ships = new ArrayList<>();
	private List<Position> targets = new ArrayList<>();

	public Player(String name) {
		this.name = name;
	}

	public void addShip(Ship s) {
		ships.add(s);
		board.addShip(s);
	}

	public boolean getShot(Position shot) {
		return board.addShot(shot);
	}

	public void shotEnemy(Position shot, boolean hit) {
		if (targets.contains(shot))
			System.out.println("Position has already been shot");
		State state = hit ? State.HIT : State.MISS;
		enemyBoard.setState(shot, state);
	}

	public boolean hasAliveShip() {
		return board.hasAliveShip();
	}

	public void printBoard() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n   ");
		for (int i = 0; i < board.size; i++)
			builder.append(i + "  ");
		builder.append("\n");
		for (int i = 0; i < board.size; i++) {
			builder.append(ANSI.WHITE + i + "  " + ANSI.RESET);

			for (int j = 0; j < board.size; j++) {
				State cell = board.board[i][j];
				if (cell == State.WATER)
					builder.append(ANSI.BLUE + cell + ANSI.RESET);
				else if (cell == State.HIT)
					builder.append(ANSI.RED + cell + ANSI.RESET);
				else if (cell == State.MISS)
					builder.append(ANSI.WHITE + cell + ANSI.RESET);
				else
					builder.append(ANSI.YELLOW + cell + ANSI.RESET);
				builder.append("  ");
			}
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}

	public void printTwoBoards() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n   ");

		for (int i = 0; i < board.size; i++)
			builder.append(i + "  ");

		builder.append("  |       ");

		for (int i = 0; i < board.size; i++)
			builder.append(i + "  ");

		builder.append("\n");

		for (int i = 0; i < board.size; i++) {
			builder.append(ANSI.WHITE + i + ANSI.RESET + "  ");

			for (int j = 0; j < board.size; j++) {
				State cell = board.board[i][j];
				if (cell == State.WATER)
					builder.append(ANSI.BLUE + cell + ANSI.RESET);
				else if (cell == State.HIT)
					builder.append(ANSI.RED + cell + ANSI.RESET);
				else if (cell == State.MISS)
					builder.append(ANSI.WHITE + cell + ANSI.RESET);
				else
					builder.append(ANSI.YELLOW + cell + ANSI.RESET);
				builder.append("  ");
			}

			builder.append("  |    ");

			builder.append(ANSI.WHITE + i + ANSI.RESET + "  ");

			for (int j = 0; j < enemyBoard.size; j++) {
				State cell = enemyBoard.board[i][j];
				if (cell == State.WATER)
					builder.append(ANSI.BLUE + cell + ANSI.RESET);
				else if (cell == State.HIT)
					builder.append(ANSI.RED + cell + ANSI.RESET);
				else if (cell == State.MISS)
					builder.append(ANSI.WHITE + cell + ANSI.RESET);
				else
					builder.append(ANSI.YELLOW + cell + ANSI.RESET);
				builder.append("  ");
			}

			builder.append("\n");
		}
		builder.append("                                   |                                   \n");
		builder.append("           YOUR BOARD              |               ENEMY BOARD         \n\n");
		System.out.println(builder.toString());
	}
}

final class ANSI {
	public static final String RESET = "\u001B[0m";
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

	public static final String BLACK_BG = "\u001B[40m";
	public static final String RED_BG = "\u001B[41m";
	public static final String GREEN_BG = "\u001B[42m";
	public static final String YELLOW_BG = "\u001B[43m";
	public static final String BLUE_BG = "\u001B[44m";
	public static final String PURPLE_BG = "\u001B[45m";
	public static final String CYAN_BG = "\u001B[46m";
	public static final String WHITE_BG = "\u001B[47m";
}