package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import cs3343.battleship.exceptions.NullObjectException;
import cs3343.battleship.exceptions.OverlapShipException;
import cs3343.battleship.exceptions.PositionOutOfBoundsException;
import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Board;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;
import cs3343.battleship.logic.Submarine;

public class BoardTests {
    @Test
    public void test_board_to_string_1() {
        Board.State state = Board.State.HIT;
        assertEquals("×", state.toString());
    }

    @Test
    public void test_board_to_string_2() {
        Board.State state = Board.State.MISS;
        assertEquals("⋆", state.toString());
    }

    @Test
    public void test_board_to_string_3() {
        Board.State state = Board.State.SHIP;
        assertEquals("□", state.toString());
    }

    @Test
    public void test_board_to_string_4() {
        Board.State state = Board.State.WATER;
        assertEquals("~", state.toString());
    }

    @Test
    public void test_board_set_state_1() throws Exception {
        Board test = new Board(9);
        test.setState(new Position(1, 1), Board.State.MISS);
        assertEquals(Board.State.MISS, test.getState(1, 1));
    }

    @Test
    public void test_board_set_state_exception_1() {
        Board test = new Board(9);
        assertThrows(NullObjectException.class, () -> test.setState(null, Board.State.MISS));
    }

    @Test
    public void test_board_set_state_exception_2() {
        Board test = new Board(9);
        assertThrows(NullObjectException.class, () -> test.setState(new Position(1, 1), null));
    }

    @Test
    public void test_board_set_state_exception_3() {
        Board test = new Board(1);
        assertThrows(Exception.class, () -> test.setState(new Position(1, 1), Board.State.MISS));
    }

    @Test
    public void test_board_add_ship_1() throws Exception {
        Ship s = new Battleship(Direction.RIGHT, new Position(1, 1));
        Board test = new Board(9);
        test.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < 4; i++) {
            assertEquals(new Position(1, 1 + i), positions.get(i));
        }
    }

    @Test
    public void test_board_add_ship_2() throws Exception {
        Ship s = new Battleship(Direction.DOWN, new Position(1, 1));
        Board test = new Board(9);
        test.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < 4; i++) {
            assertEquals(new Position(1 + i, 1), positions.get(i));
        }
    }

    @Test
    public void test_board_add_ship_exception_1() {
        Ship s = new Battleship();
        Board test = new Board(1);
        assertThrows(NullObjectException.class, () -> test.addShip(s));
    }

    @Test
    public void test_board_add_ship_exception_2() {
        Ship s = new Battleship();
        s.setDirection(Direction.DOWN);
        Board test = new Board(1);
        assertThrows(NullObjectException.class, () -> test.addShip(s));
    }

    @Test
    public void test_board_add_ship_exception_3() {
        Ship s = new Battleship(Direction.RIGHT, new Position(1, 1));
        Board test = new Board(1);
        assertThrows(PositionOutOfBoundsException.class, () -> test.addShip(s));
    }

    @Test
    public void test_board_add_ship_exception_4() {
        Ship s = new Battleship(Direction.RIGHT, new Position(1, 1));
        Board test = new Board(3);
        assertThrows(PositionOutOfBoundsException.class, () -> test.addShip(s));
    }

    @Test
    public void test_board_add_ship_exception_5() {
        Ship s = new Battleship(Direction.DOWN, new Position(1, 1));
        Board test = new Board(3);
        assertThrows(PositionOutOfBoundsException.class, () -> test.addShip(s));
    }

    @Test
    public void test_board_add_ship_exception_6() throws Exception {
        Ship s = new Battleship(Direction.RIGHT, new Position(1, 1));
        Board test = new Board(9);
        test.addShip(s);
        assertThrows(OverlapShipException.class, () -> test.addShip(s));
    }

    @Test
    public void test_board_add_ship_exception_7() throws Exception {
        Ship s1 = new Battleship(Direction.RIGHT, new Position(1, 0));
        Ship s2 = new Battleship(Direction.DOWN, new Position(0, 1));
        Board test = new Board(9);
        test.addShip(s1);
        assertThrows(OverlapShipException.class, () -> test.addShip(s2));
    }

    @Test
    public void test_board_add_shot_1() throws Exception {
        Position shot = new Position(1, 1);
        Board test = new Board(9);
        boolean b = test.addShot(shot);
        assertFalse(b);
    }

    @Test
    public void test_board_add_shot_2() throws Exception {
        Ship s = new Battleship(Direction.DOWN, new Position(1, 1));
        Position shot = new Position(1, 1);
        Board test = new Board(9);
        test.addShip(s);
        boolean b = test.addShot(shot);
        assertTrue(b);
    }

    @Test
    public void test_board_add_shot_exception_1() throws Exception {
        Ship s = new Battleship(Direction.DOWN, new Position(1, 1));
        Position shot = new Position(1, 1);
        Board test = new Board(9);
        test.addShip(s);
        test.addShot(shot);
        assertThrows(PositionShotTwiceException.class, () -> test.addShot(shot));
    }

    @Test
    public void test_board_add_shot_exception_2() {
        Board test = new Board(9);
        assertThrows(NullObjectException.class, () -> test.addShot(null));
    }

    @Test
    public void test_board_has_alive_ship_1() {
        Board test = new Board(5);
        boolean b = test.hasAliveShip();
        assertFalse(b);
    }

    @Test
    public void test_board_has_alive_ship_2() throws Exception {
        Board test = new Board(5);
        Ship s = new Submarine(Direction.DOWN, new Position(1, 1));
        test.addShip(s);
        boolean b = test.hasAliveShip();
        assertTrue(b);
    }
}
