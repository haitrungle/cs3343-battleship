package cs3343.battleship.exceptions;

public class PositionOutOfBoundsException extends InvalidInputException {
    public PositionOutOfBoundsException(int row, int col, int size) {
        super("Position (" + row + "," + col + ") is out of bounds. Row and column must be between 0 and " + (size - 1) + ".");
    }
}
