package cs3343.battleship.game;

import java.util.ArrayList;
import java.util.List;

import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.logic.Board;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Board.State;
import cs3343.battleship.logic.ship.Ship;

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

	public boolean getShot(Position shot) throws PositionShotTwiceException {
		return board.addShot(shot);
	}

	public void shotEnemy(Position shot, boolean hit) throws PositionShotTwiceException {
		if (hasShotEnemyAt(shot))
			throw new PositionShotTwiceException(shot);
		State state = hit ? State.HIT : State.MISS;
		enemyBoard.setState(shot, state);
		targets.add(shot);
	}

	public boolean hasShotEnemyAt(Position shot) {
		return targets.contains(shot);
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
			builder.append(Console.WHITE + i + "  " + Console.RESET);

			for (int j = 0; j < board.size; j++) {
				State cell = board.board[i][j];
				if (cell == State.WATER)
					builder.append(Console.BLUE + cell + Console.RESET);
				else if (cell == State.HIT)
					builder.append(Console.RED + cell + Console.RESET);
				else if (cell == State.MISS)
					builder.append(Console.WHITE + cell + Console.RESET);
				else
					builder.append(Console.YELLOW + cell + Console.RESET);
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
			builder.append(Console.WHITE + i + Console.RESET + "  ");

			for (int j = 0; j < board.size; j++) {
				State cell = board.board[i][j];
				if (cell == State.WATER)
					builder.append(Console.BLUE + cell + Console.RESET);
				else if (cell == State.HIT)
					builder.append(Console.RED + cell + Console.RESET);
				else if (cell == State.MISS)
					builder.append(Console.WHITE + cell + Console.RESET);
				else
					builder.append(Console.YELLOW + cell + Console.RESET);
				builder.append("  ");
			}

			builder.append("  |    ");

			builder.append(Console.WHITE + i + Console.RESET + "  ");

			for (int j = 0; j < enemyBoard.size; j++) {
				State cell = enemyBoard.board[i][j];
				if (cell == State.WATER)
					builder.append(Console.BLUE + cell + Console.RESET);
				else if (cell == State.HIT)
					builder.append(Console.RED + cell + Console.RESET);
				else if (cell == State.MISS)
					builder.append(Console.WHITE + cell + Console.RESET);
				else
					builder.append(Console.YELLOW + cell + Console.RESET);
				builder.append("  ");
			}

			builder.append("\n");
		}
		builder.append("                                   |                                   \n");
		builder.append("           YOUR BOARD              |               ENEMY BOARD         \n\n");
		System.out.println(builder.toString());
	}

    public Position hasOverlapShip(Ship ship) {
        for (Ship s : ships) {
			Position p = Ship.overlapPosition(s, ship);
			if (p != null) return p;
		}
		return null;
    }
}