package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import cs3343.battleship.exceptions.NullObjectException;
import cs3343.battleship.exceptions.OverlapShipException;
import cs3343.battleship.exceptions.PositionOutOfBoundsException;
import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.logic.AircraftCarrier;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Board;
import cs3343.battleship.logic.Destroyer;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;
import cs3343.battleship.logic.Submarine;

public class BoardTests {
    static Stream<Position> positionProvider() {
        return Stream.of(
                new Position(1, 2),
                new Position(2, 1),
                new Position(4, 5),
                new Position(3, 6),
                new Position(5, 0),
                new Position(0, 5));
    }

    static Stream<Position> positionOutOfBoundsProvider() {
        return Stream.of(
                new Position(1, -1),
                new Position(-1, 1),
                new Position(0, 2),
                new Position(3, 2),
                new Position(10, 0),
                new Position(0, 10));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    public void setState_shouldGetSameState(Position pos) throws Exception {
        Board test = new Board(9);
        test.setState(pos, Board.State.MISS);
        assertEquals(Board.State.MISS, test.getState(pos.row, pos.col));
    }

    @ParameterizedTest
    @MethodSource("positionOutOfBoundsProvider")
    public void setStateInvalidPosition_shouldThrow() {
        Board test = new Board(1);
        assertThrows(PositionOutOfBoundsException.class, () -> test.setState(new Position(1, 1), Board.State.MISS));
    }

    @Test
    public void setStateAtNullPosition_shouldThrow() {
        Board test = new Board(9);
        assertThrows(NullObjectException.class, () -> test.setState(null, Board.State.MISS));
    }

    @Test
    public void setNullState_shouldThrow() {
        Board test = new Board(2);
        assertThrows(NullObjectException.class, () -> test.setState(new Position(1, 1), null));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    public void addShipRight_shouldSetCorrectPositions(Position pos) throws Exception {
        Ship s = new Battleship(Direction.RIGHT, pos);
        Board test = new Board(10);
        test.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < 4; i++) {
            assertEquals(new Position(pos.row, pos.col + i), positions.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    public void addShipDown_shouldSetCorrectPositions(Position pos) throws Exception {
        Ship s = new Battleship(Direction.DOWN, pos);
        Board test = new Board(10);
        test.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < 4; i++) {
            assertEquals(new Position(pos.row + i, pos.col), positions.get(i));
        }
    }

    @Test
    public void addShipWithoutDirection_shouldThrow() {
        Ship s = new Battleship();
        s.setStartPosition(new Position(1, 1));
        Board test = new Board(1);
        assertThrows(NullObjectException.class, () -> test.addShip(s));
    }

    @Test
    public void addShipWithoutStartPosition_shouldThrow() {
        Ship s = new Battleship();
        s.setDirection(Direction.DOWN);
        Board test = new Board(1);
        assertThrows(NullObjectException.class, () -> test.addShip(s));
        assertFalse(test.hasAliveShip());
    }

    @ParameterizedTest
    @MethodSource("positionOutOfBoundsProvider")
    public void addShipWithInvalidPosition_shouldThrow(Position pos) {
        Ship s = new AircraftCarrier(Direction.RIGHT, pos);
        Board test = new Board(6);
        assertThrows(PositionOutOfBoundsException.class, () -> test.addShip(s));
        assertFalse(test.hasAliveShip());
    }

    static Stream<Position> overlapShipProvider() {
        return Stream.of(
                new Position(1, 2),
                new Position(2, 1),
                new Position(4, 5),
                new Position(3, 6),
                new Position(5, 0),
                new Position(0, 5));
    }

    @ParameterizedTest
    @MethodSource("overlapShipProvider")
    public void addSameShipTwice_shouldThrow(Position pos) throws Exception {
        Ship s = new Battleship(Direction.RIGHT, pos);
        Board test = new Board(10);
        test.addShip(s);
        assertThrows(OverlapShipException.class, () -> test.addShip(s));
    }

    @ParameterizedTest
    @MethodSource("overlapShipProvider")
    public void addOverlapShipSameType_shouldThrow(Position pos) throws Exception {
        Ship s1 = new Battleship(Direction.RIGHT, pos);
        Ship s2 = new Battleship(Direction.DOWN, pos);
        Board test = new Board(10);
        test.addShip(s1);
        assertThrows(OverlapShipException.class, () -> test.addShip(s2));
    }

    @ParameterizedTest
    @MethodSource("overlapShipProvider")
    public void addOverlapShipDifferentType_shouldThrow(Position pos) throws Exception {
        Ship s1 = new Battleship(Direction.RIGHT, pos);
        Board test = new Board(10);
        test.addShip(s1);
        for (Position p : s1.positions()) {
            Ship s2 = new Destroyer(Direction.DOWN, p);
            assertThrows(OverlapShipException.class, () -> test.addShip(s2));
        }
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    public void addShotEmptyBoard_shouldGiveFalse(Position shot) throws Exception {
        Board test = new Board(9);
        boolean hit = test.addShot(shot);
        assertFalse(hit);
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    public void addShotAtShip_shouldGiveTrue(Position pos) throws Exception {
        Ship s = new Battleship(Direction.DOWN, pos);
        Board test = new Board(9);
        test.addShip(s);
        for (Position p : s.positions()) {
            boolean hit = test.addShot(p);
            assertTrue(hit);
        }
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    public void addShotSameTwice_shouldThrow(Position pos) throws Exception {
        Ship s = new Battleship(Direction.DOWN, pos);
        Board test = new Board(9);
        test.addShip(s);
        test.addShot(pos);
        assertThrows(PositionShotTwiceException.class, () -> test.addShot(pos));
    }

    @Test
    public void addNullShot_shouldThrow() {
        Board test = new Board(9);
        assertThrows(NullObjectException.class, () -> test.addShot(null));
    }

    @Test
    public void newBoard_shouldNotHaveAliveShip() {
        Board test = new Board(9);
        boolean hasShip = test.hasAliveShip();
        assertFalse(hasShip);
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    public void afterAddShip_shouldHaveAliveShip(Position pos) throws Exception {
        Board test = new Board(9);
        Ship s = new Submarine(Direction.DOWN, pos);
        test.addShip(s);
        boolean hasShip = test.hasAliveShip();
        assertTrue(hasShip);
    }
}
