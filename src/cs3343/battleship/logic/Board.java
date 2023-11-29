package cs3343.battleship.logic;

import java.util.Arrays;

import cs3343.battleship.exceptions.OverlapShipException;
import cs3343.battleship.exceptions.PositionOutOfBoundsException;
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

    public void addShip(Ship s) throws PositionOutOfBoundsException, OverlapShipException {
        // Change states if and only if the ship can be added successfully
        // In other words, this method is "atomic": if it throws, the board is unchanged
        int row = s.getStartPosition().row;
        int col = s.getStartPosition().col;
        int len = s.getLength();
        if (row < 0 || row >= size || col < 0 || col >= size)
            throw new PositionOutOfBoundsException(row, col, size);
        if (s.getDirection() == Direction.RIGHT) {
            if (col + len > size)
                throw new PositionOutOfBoundsException(row, col + len - 1, size);
            for (int j = col; j < col + len; j++) {
                if (board[row][j] != State.WATER)
                    throw new OverlapShipException(new Position(row, j));
            }
            for (int j = col; j < col + len; j++) {
                board[row][j] = State.SHIP;
            }
        } else {
            if (row + len > size)
                throw new PositionOutOfBoundsException(row + len - 1, col, size);
            for (int i = row; i < row + len; i++) {
                if (board[i][col] != State.WATER)
                    throw new OverlapShipException(new Position(i, col));
            }
            for (int i = row; i < row + len; i++) {
                board[i][col] = State.SHIP;
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

    public void setState(Position position, State state) throws PositionOutOfBoundsException {
        int row = position.row;
        int col = position.col;
        if (row >= size || col >= size)
            throw new PositionOutOfBoundsException(row, col, size);
        board[row][col] = state;
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
