package cs3343.battleship.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3343.battleship.exceptions.OverlapShipException;
import cs3343.battleship.exceptions.PositionOutOfBoundsException;
import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.logic.*;
import cs3343.battleship.logic.Board.State;
import cs3343.battleship.logic.ship.Ship;

public final class Player {
	private Board board = new Board(Config.BOARD_SIZE);
	private Board enemyBoard = new Board(Config.BOARD_SIZE);
	private List<Ship> ships = new ArrayList<>();
	private List<Position> targets = new ArrayList<>();

	public void addShip(Ship s) throws OverlapShipException, PositionOutOfBoundsException {
		board.addShip(s);
		ships.add(s);
	}

	public Ship addShipRandom(Ship ship) {
		Random rng = new Random(145);
		while (true) {
			Position p = Position.random(rng, board.size);
			ship.setStartPosition(p);
			Direction direction = Direction.random(rng);
			ship.setDirection(direction);
			try {
				addShip(ship);
				return ship;
			} catch (OverlapShipException | PositionOutOfBoundsException e) {
				continue;
			}
		}
	}

	public boolean getShot(Position shot) throws PositionShotTwiceException {
		return board.addShot(shot);
	}

	public void shotEnemy(Position shot, boolean hit) throws PositionShotTwiceException, PositionOutOfBoundsException {
		if (hasShotEnemyAt(shot))
			throw new PositionShotTwiceException(shot);
		State state = hit ? State.HIT : State.MISS;
		enemyBoard.setState(shot, state);
		targets.add(shot);
	}

	public void checkShot(Position shot) throws PositionShotTwiceException, PositionOutOfBoundsException {
		if (hasShotEnemyAt(shot))
			throw new PositionShotTwiceException(shot);
		if (shot.row < 0 || shot.row >= board.size || shot.col < 0 || shot.col >= board.size)
			throw new PositionOutOfBoundsException(shot.row, shot.col, board.size);
	}

	public boolean hasShotEnemyAt(Position shot) {
		return targets.contains(shot);
	}

	public boolean hasAliveShip() {
		return board.hasAliveShip();
	}

	public Position hasOverlapShip(Ship ship) {
		for (Ship s : ships) {
			Position p = Ship.overlapPosition(s, ship);
			if (p != null)
				return p;
		}
		return null;
	}

	public Position getRandomShot() {
		Random rng = new Random(145);
		while (true) {
			Position p = Position.random(rng, board.size);
			if (hasShotEnemyAt(p))
				continue;
			return p;
		}
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
		Console.println(builder.toString());
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
		Console.println(builder.toString());
	}
}