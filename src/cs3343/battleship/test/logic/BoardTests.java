package cs3343.battleship.test.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import cs3343.battleship.exceptions.NullObjectException;
import cs3343.battleship.exceptions.OverlapShipException;
import cs3343.battleship.exceptions.PositionOutOfBoundsException;
import cs3343.battleship.logic.Board;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.Battleship;
import cs3343.battleship.logic.ship.Direction;
import cs3343.battleship.logic.ship.Ship;
import cs3343.battleship.logic.ship.Submarine;

public class BoardTests {
    @Test
    public void test_board_to_string_1() throws Exception {
        Board.State state = Board.State.HIT;
        assertEquals("×", state.toString());
    }

    @Test
    public void test_board_to_string_2() throws Exception {
        Board.State state = Board.State.MISS;
        assertEquals("⋆", state.toString());
    }

    @Test
    public void test_board_to_string_3() throws Exception {
        Board.State state = Board.State.SHIP;
        assertEquals("□", state.toString());
    }

    @Test
    public void test_board_to_string_4() throws Exception {
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
    public void test_board_set_state_exception_1() throws Exception {
        Board test = new Board(9);
        Exception e = assertThrows(Exception.class, () -> test.setState(null, Board.State.MISS));
        assertEquals("An object of class cs3343.battleship.logic.Position is null", e.getMessage());
    }

    @Test
    public void test_board_set_state_exception_2() throws Exception {
        Board test = new Board(9);
        Exception e = assertThrows(Exception.class, () -> test.setState(new Position(1, 1), null));
        assertEquals("An object of class cs3343.battleship.logic.Board$State is null", e.getMessage());
    }

    @Test
    public void test_board_set_state_exception_3() throws Exception {
        Board test = new Board(1);
        Exception e = assertThrows(Exception.class, () -> test.setState(new Position(1, 1), Board.State.MISS));
        assertEquals(
                "Invalid input: Position (1,1) is out of bounds. Row and column must be between 0 and " + (1 - 1) + ".",
                e.getMessage());
    }

    @Test
    public void test_board_add_ship_1() throws Exception {
        Ship s = new Battleship(Direction.decode("r"), new Position(1, 1));
        Board test = new Board(9);
        test.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < 4; i++) {
            assertEquals(new Position(1, 1 + i), positions.get(i));
        }
    }

    @Test
    public void test_board_add_ship_2() throws Exception {
        Ship s = new Battleship(Direction.decode("d"), new Position(1, 1));
        Board test = new Board(9);
        test.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < 4; i++) {
            assertEquals(new Position(1 + i, 1), positions.get(i));
        }
    }

    @Test
    public void test_board_add_ship_exception_1() throws Exception {
        Ship s = new Battleship();
        Board test = new Board(1);
        Exception e = assertThrows(Exception.class, () -> test.addShip(s));
        assertEquals("An object of class cs3343.battleship.logic.ship.Ship is null", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_2() throws Exception {
        Ship s = new Battleship();
        s.setDirection(Direction.DOWN);
        Board test = new Board(1);
        Exception e = assertThrows(Exception.class, () -> test.addShip(s));
        assertEquals("An object of class cs3343.battleship.logic.Position is null", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_3() throws Exception {
        Ship s = new Battleship(Direction.decode("r"), new Position(1, 1));
        Board test = new Board(1);
        Exception e = assertThrows(Exception.class, () -> test.addShip(s));
        assertEquals("Invalid input: Position (" + 1 + "," + 1
                + ") is out of bounds. Row and column must be between 0 and " + (1 - 1) + ".", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_4() throws Exception {
        Ship s = new Battleship(Direction.decode("r"), new Position(1, 1));
        ;
        Board test = new Board(3);
        Exception e = assertThrows(Exception.class, () -> test.addShip(s));
        assertEquals("Invalid input: Position (" + 1 + "," + 4
                + ") is out of bounds. Row and column must be between 0 and " + (3 - 1) + ".", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_5() throws Exception {
        Ship s = new Battleship(Direction.decode("d"), new Position(1, 1));
        Board test = new Board(3);
        Exception e = assertThrows(Exception.class, () -> test.addShip(s));
        assertEquals("Invalid input: Position (" + 4 + "," + 1
                + ") is out of bounds. Row and column must be between 0 and " + (3 - 1) + ".", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_6() throws Exception {
        Ship s = new Battleship(Direction.decode("r"), new Position(1, 1));
        ;
        Board test = new Board(9);
        test.addShip(s);
        Exception e = assertThrows(Exception.class, () -> test.addShip(s));
        assertEquals("Invalid input: There is an overlapping ship at " + "1,1"
                + ". Please choose another location for your ship.", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_7() throws Exception {
        Ship s = new Battleship(Direction.decode("d"), new Position(1, 1));
        ;
        Board test = new Board(9);
        test.addShip(s);
        Exception e = assertThrows(Exception.class, () -> test.addShip(s));
        assertEquals("Invalid input: There is an overlapping ship at " + "1,1"
                + ". Please choose another location for your ship.", e.getMessage());
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
        Ship s = new Battleship(Direction.decode("d"), new Position(1, 1));
        ;
        Position shot = new Position(1, 1);
        Board test = new Board(9);
        test.addShip(s);
        boolean b = test.addShot(shot);
        assertTrue(b);
    }

    @Test
    public void test_board_add_shot_exception_1() throws Exception {
        Ship s = new Battleship(Direction.decode("d"), new Position(1, 1));
        ;
        Position shot = new Position(1, 1);
        Board test = new Board(9);
        test.addShip(s);
        test.addShot(shot);
        Exception e = assertThrows(Exception.class, () -> test.addShot(shot));
        assertEquals("Invalid input: You have already shot at " + "1,1"
                + ". Please select another position.", e.getMessage());
    }

    @Test
    public void test_board_add_shot_exception_2() throws Exception {
        Board test = new Board(9);
        Exception e = assertThrows(Exception.class, () -> test.addShot(null));
        assertEquals("An object of class cs3343.battleship.logic.Position is null", e.getMessage());
    }

    @Test
    public void test_board_has_alive_ship_1() {
        Board test = new Board(5);
        boolean b = test.hasAliveShip();
        assertFalse(b);
    }

    @Test
    public void test_board_has_alive_ship_2() throws OverlapShipException,
            PositionOutOfBoundsException, NullObjectException {
        Board test = new Board(5);
        Ship s = new Submarine(Direction.DOWN, new Position(1, 1));
        test.addShip(s);
        boolean b = test.hasAliveShip();
        assertTrue(b);
    }
}
