package cs3343.battleship.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3343.battleship.exceptions.NullObjectException;
import cs3343.battleship.exceptions.OverlapShipException;
import cs3343.battleship.exceptions.PositionOutOfBoundsException;
import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.game.Console;
import cs3343.battleship.logic.Board.State;

/**
 * This class represents a player in the game. It integrates the components of
 * the logic package in a unified class.
 */
public final class Player {
    private final Board board;
    private final Board enemyBoard;
    private final List<Ship> ships = new ArrayList<>();
    private final List<Position> targets = new ArrayList<>();

    public Player(int boardSize) {
        board = new Board(boardSize);
        enemyBoard = new Board(boardSize);
    }

    /**
     * Adds a ship to this player's board.
     * 
     * @throws NullObjectException
     * @throws OverlapShipException
     * @throws PositionOutOfBoundsException
     */
    public void addShip(Ship s) throws PositionOutOfBoundsException, OverlapShipException, NullObjectException {
        board.addShip(s);
        ships.add(s);
    }

    /**
     * Adds a ship to this player's board at a random position and returns the ship.
     * 
     * @param ship The ship to add, whose startPosition and direction may be unset.
     * @return The ship that was added, with startPosition and direction set.
     */
    public Ship addShipRandom(Ship ship, Random rng) {
        while (true) {
            Position p = Position.random(rng, board.getSize());
            ship.setStartPosition(p);
            Direction direction = Direction.random(rng);
            ship.setDirection(direction);
            try {
                addShip(ship);
                return ship;
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * Returns the board size of this player.
     * 
     * @return the board size of this player
     */
    public int getBoardSize() {
        return board.getSize();
    }

    /**
     * Adds a shot to this player's board.
     * 
     * @param shot the position of the shot
     * @return true if the shot hits a ship, false otherwise
     * @throws NullObjectException
     * @throws PositionShotTwiceException
     */
    public boolean getShot(Position shot) throws PositionShotTwiceException, NullObjectException {
        return board.addShot(shot);
    }

    /**
     * Adds a shot to this player's enemy board.
     * 
     * @param shot the position of the shot
     * @param hit  true if the shot hits a ship, false otherwise
     */
    public void shotEnemy(Position shot, boolean hit)
            throws NullObjectException, PositionOutOfBoundsException, PositionShotTwiceException {
        if (hasShotEnemyAt(shot))
            throw new PositionShotTwiceException(shot);
        State state = hit ? State.HIT : State.MISS;
        enemyBoard.setState(shot, state);
        targets.add(shot);
    }

    /**
     * Thows an exception if this player has shot at the given position or if the
     * position is out of bounds.
     * 
     * @param shot the position to check
     * @throws PositionShotTwiceException   if this player has shot at the given
     *                                      position
     * @throws PositionOutOfBoundsException if the position is out of bounds
     */
    public void checkShot(Position shot) throws PositionShotTwiceException, PositionOutOfBoundsException {
        if (hasShotEnemyAt(shot))
            throw new PositionShotTwiceException(shot);
        if (shot.row < 0 || shot.row >= board.getSize() || shot.col < 0 || shot.col >= board.getSize())
            throw new PositionOutOfBoundsException(shot.row, shot.col, board.getSize());
    }

    /**
     * Returns true if this player has shot at the given position. Currently this
     * method is only used for testing.
     * 
     * @param shot the position to check
     * @return true if this player has shot the enemy at the given position
     */
    public boolean hasShotEnemyAt(Position shot) {
        return targets.contains(shot);
    }

    /**
     * Returns true if this player is still alive.
     * 
     * @return true if this player has at least one alive ship, false otherwise
     */
    public boolean hasAliveShip() {
        return board.hasAliveShip();
    }

    /**
     * Returns true if this player has a ship overlapping with the given ship.
     * 
     * @param ship the ship to check
     * @return an overlapping position, or null if no such ship exists
     */
    public Position hasOverlapShip(Ship ship) {
        for (Ship s : ships) {
            Position p = Ship.overlapPosition(s, ship);
            if (p != null)
                return p;
        }
        return null;
    }

    /**
     * Returns a random shot that this player has not shot at yet.
     * 
     * @return a random shot that this player has not shot at yet
     */
    public Position getRandomShot(Random rng) {
        while (true) {
            Position p = Position.random(rng, board.getSize());
            if (hasShotEnemyAt(p))
                continue;
            return p;
        }
    }

    /**
     * Returns a string representation of this player's board for printing.
     * 
     * @return the string representation of this player's board
     */
    public String boardToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n   ");
        for (int i = 0; i < board.getSize(); i++)
            builder.append(i + "  ");
        builder.append("\n");
        for (int i = 0; i < board.getSize(); i++) {
            builder.append(Console.colorize(i + "  ", Console.Color.WHITE));

            for (int j = 0; j < board.getSize(); j++) {
                State cell = board.getState(i, j);
                if (cell == State.WATER)
                    builder.append(Console.colorize(cell, Console.Color.BLUE));
                else if (cell == State.HIT)
                    builder.append(Console.colorize(cell, Console.Color.RED));
                else if (cell == State.MISS)
                    builder.append(Console.colorize(cell, Console.Color.WHITE));
                else
                    builder.append(Console.colorize(cell, Console.Color.YELLOW));
                builder.append("  ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Returns a string representation of this player's board and enemy board for
     * printing.
     * 
     * @return the string representation of this player's board and enemy board
     */
    public String twoBoardsToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n   ");

        for (int i = 0; i < board.getSize(); i++)
            builder.append(i + "  ");

        builder.append("  |       ");

        for (int i = 0; i < board.getSize(); i++)
            builder.append(i + "  ");

        builder.append("\n");

        for (int i = 0; i < board.getSize(); i++) {
            builder.append(Console.colorize(i + "  ", Console.Color.WHITE));

            for (int j = 0; j < board.getSize(); j++) {
                State cell = board.getState(i, j);
                if (cell == State.WATER)
                    builder.append(Console.colorize(cell, Console.Color.BLUE));
                else if (cell == State.HIT)
                    builder.append(Console.colorize(cell, Console.Color.RED));
                else if (cell == State.MISS)
                    builder.append(Console.colorize(cell, Console.Color.WHITE));
                else
                    builder.append(Console.colorize(cell, Console.Color.YELLOW));
                builder.append("  ");
            }

            builder.append("  |    ");

            builder.append(Console.colorize(i + "  ", Console.Color.WHITE));

            for (int j = 0; j < enemyBoard.getSize(); j++) {
                State cell = enemyBoard.getState(i, j);
                if (cell == State.WATER)
                    builder.append(Console.colorize(cell, Console.Color.BLUE));
                else if (cell == State.HIT)
                    builder.append(Console.colorize(cell, Console.Color.RED));
                else if (cell == State.MISS)
                    builder.append(Console.colorize(cell, Console.Color.WHITE));
                else
                    builder.append(Console.colorize(cell, Console.Color.YELLOW));
                builder.append("  ");
            }

            builder.append("\n");
        }
        builder.append("                                   |                                   \n");
        builder.append("           YOUR BOARD              |               ENEMY BOARD         \n\n");
        return builder.toString();
    }
}