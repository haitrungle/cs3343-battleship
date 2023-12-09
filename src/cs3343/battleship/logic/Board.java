package cs3343.battleship.logic;

import java.util.Arrays;

import cs3343.battleship.exceptions.NullObjectException;
import cs3343.battleship.exceptions.OverlapShipException;
import cs3343.battleship.exceptions.PositionOutOfBoundsException;
import cs3343.battleship.exceptions.PositionShotTwiceException;

/**
 * This class represents a game board, which is internally a square grid of
 * cells with a fixed size. Each cell is in one of four states: WATER, SHIP,
 * MISS, or HIT (see the {@link State} enum). The class strictly contains
 * information about the board states and does not contain anything about the
 * ships on the board or the shots fired on the board. Those are managed by
 * the higher-level {@link Player} class.
 * 
 * Technically, there are two ways this class is used: either to emulate the
 * player's own board or to emulate the enemy's board. The former has full
 * information about the board state and is always correct, while the latter
 * only has partial information about the board state, as the enemy's ships
 * are supposedly unknown and do not appear on the board until hit. This
 * neccessitates the use of two different methods to shoot at the board
 * (see {@link #addShot(Position)} and {@link #setState(Position, State)}).
 */
public final class Board {
    // Javadoc
    /**
     * The state of a position on the board
     * 
     * @see #HIT
     * @see #MISS
     * @see #SHIP
     * @see #WATER
     */
    public enum State {
        /**
         * HIT: a SHIP cell is hit at this position
         */
        HIT,
        /**
         * MISS: a WATER cell is hit at this position
         */
        MISS,
        /**
         * SHIP: a ship is at this position
         */
        SHIP,
        /**
         * WATER: no ship is at this position
         */
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
    private final int size;
    private final State[][] grid;

    /**
     * Constructs a Board with the given size, i.e. its width and height.
     * 
     * @param size the width and height of the square grid
     */
    public Board(int size) {
        this.size = size;
        grid = initGrid(size);
    }

    /**
     * Returns the size of the board.
     * 
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the state of a position on the board.
     * 
     * @param row the row of the position
     * @param col the column of the position
     * @return the state of the position
     * @throws PositionOutOfBoundsException if the position is out of bounds
     */
    public State getState(int row, int col) {
        return grid[row][col];
    }

    private static State[][] initGrid(int size) {
        State[][] table = new State[size][size];
        for (State[] row : table) {
            Arrays.fill(row, State.WATER);
        }
        return table;
    }

    /**
     * Adds a ship to the board. A ship can be added if and only if it does not
     * overlap with any HIT, MISS, or SHIP cells; in other words, if all of the Ship
     * positions are currently WATER cells.
     * 
     * <p>
     * If the ship cannot be added successfully, this method throws and the board is
     * unchanged.
     * 
     * @param ship the ship to be added
     * @throws PositionOutOfBoundsException if the ship position is out of bounds
     * @throws OverlapShipException         if the ship overlaps with a previously
     *                                      added ship
     * @throws NullObjectException          if ship has a null field
     */
    public void addShip(Ship ship)
            throws PositionOutOfBoundsException, OverlapShipException, NullObjectException {
        /*
         * Check if the entire ship can be added successfully before actually adding it,
         * ensuring the method is "atomic": if it throws, the board is unchanged.
         */
        if (ship == null)
            throw new NullObjectException(Ship.class);
        if (ship.getDirection() == null)
            throw new NullObjectException(Ship.class);
        if (ship.getStartPosition() == null)
            throw new NullObjectException(Position.class);

        int row = ship.getStartPosition().row;
        int col = ship.getStartPosition().col;
        int len = ship.getLength();

        if (row < 0 || row >= size || col < 0 || col >= size)
            throw new PositionOutOfBoundsException(row, col, size);
        if (ship.getDirection() == Direction.RIGHT) {
            if (col + len > size)
                throw new PositionOutOfBoundsException(row, col + len - 1, size);
            for (int j = col; j < col + len; j++) {
                if (grid[row][j] != State.WATER)
                    throw new OverlapShipException(new Position(row, j));
            }
            for (int j = col; j < col + len; j++) {
                grid[row][j] = State.SHIP;
            }
        } else {
            if (row + len > size)
                throw new PositionOutOfBoundsException(row + len - 1, col, size);
            for (int i = row; i < row + len; i++) {
                if (grid[i][col] != State.WATER)
                    throw new OverlapShipException(new Position(i, col));
            }
            for (int i = row; i < row + len; i++) {
                grid[i][col] = State.SHIP;
            }
        }
    }

    /**
     * Adds a shot to the board. A shot can be added if and only if it does not
     * overlap with any other previous shots, i.e. it is not a HIT or MISS cell.
     * 
     * <p>
     * If the shot cannot be added successfully, this method throws and the board is
     * unchanged.
     * 
     * @param shot the shot to be added
     * @return true if the shot hits a ship, false if it misses
     * @throws PositionShotTwiceException if the shot position is already a HIT or
     *                                    MISS cell
     * @throws NullObjectException        if the input shot is null
     */
    public boolean addShot(Position shot) throws PositionShotTwiceException, NullObjectException {
        if (shot == null)
            throw new NullObjectException(Position.class);

        int row = shot.row;
        int col = shot.col;
        switch (this.getState(row, col)) {
            case HIT:
            case MISS:
                throw new PositionShotTwiceException(shot);
            case WATER:
                grid[row][col] = State.MISS;
                return false;
            case SHIP:
                grid[row][col] = State.HIT;
                return true;
            default:
                System.out.println("Unreachable");
        }
        return false;
    }

    /**
     * Sets the state of a position on the board. Ideally, this method should not
     * be public. However, it is necessary for the {@link Player} class to set the
     * state of the enemy board when a shot is fired.
     * 
     * @param position the position to be set
     * @param state    the state to be set
     * @throws NullObjectException          if the input position or state is null
     * @throws PositionOutOfBoundsException if the position is out of bounds
     */
    public void setState(Position position, State state) throws NullObjectException, PositionOutOfBoundsException {
        if (position == null)
            throw new NullObjectException(Position.class);
        if (state == null)
            throw new NullObjectException(State.class);

        int row = position.row;
        int col = position.col;
        if (row >= size || col >= size)
            throw new PositionOutOfBoundsException(row, col, size);
        grid[row][col] = state;
    }

    /**
     * Returns true if the board has any alive ships, i.e. any SHIP cells.
     * 
     * @return true if the board has any alive ships, false otherwise
     */
    public boolean hasAliveShip() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == State.SHIP)
                    return true;
            }
        }
        return false;
    }
}
