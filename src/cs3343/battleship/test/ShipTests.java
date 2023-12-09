package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import cs3343.battleship.exceptions.InvalidInputException;
import cs3343.battleship.logic.AircraftCarrier;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Cruiser;
import cs3343.battleship.logic.Destroyer;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;
import cs3343.battleship.logic.Submarine;

public class ShipTests {
    @Test
    public void test_ship_get_name_1() {
        Ship s = new AircraftCarrier();
        assertEquals("Aircraft Carrier", s.getName());
    }

    @Test
    public void test_ship_get_name_2() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1, 1));
        assertEquals("Aircraft Carrier", s.getName());
    }

    @Test
    public void test_ship_get_length_1() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1, 1));
        assertEquals(5, s.getLength());
    }

    @Test
    public void test_ship_get_direction_1() throws InvalidInputException {
        Direction dir = Direction.decode("d");
        Ship s = new AircraftCarrier(dir, new Position(1, 1));
        assertEquals(dir, s.getDirection());
    }

    @Test
    public void test_ship_get_start_position_1() throws InvalidInputException {
        Direction dir = Direction.decode("d");
        Position p = new Position(1, 1);
        Ship s = new AircraftCarrier(dir, p);
        assertEquals(p, s.getStartPosition());
    }

    @Test
    public void test_ship_set_direction_1() throws InvalidInputException {
        Direction dir = Direction.decode("r");
        Position p = new Position(1, 1);
        Ship s = new AircraftCarrier(Direction.decode("d"), p);
        s.setDirection(dir);
        assertEquals(dir, s.getDirection());
    }

    @Test
    public void test_ship_set_start_position_1() throws InvalidInputException {
        Position p = new Position(1, 1);
        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1, 1));
        s.setStartPosition(p);
        assertEquals(p, s.getStartPosition());
    }

    @Test
    public void test_ship_introduce_1() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1, 1));

        assertEquals("Aircraft Carrier" + " (length " + 5 + ")", s.introduce());
    }

    @Test
    public void test_ship_positions_1() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1, 1));
        List<Position> positions = s.positions();
        for (int i = 0; i < 5; i++) {
            assertEquals(new Position(1 + i, 1), positions.get(i));
        }
    }

    @Test
    public void test_ship_positions_2() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("r"), new Position(1, 1));
        List<Position> positions = s.positions();
        for (int i = 0; i < 5; i++) {
            assertEquals(new Position(1, 1 + i), positions.get(i));
        }
    }

    @Test
    public void test_ship_overlap_position_1() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("r"), new Position(1, 1));
        Ship s1 = new AircraftCarrier(Direction.decode("r"), new Position(1, 1));
        Position p = Ship.overlapPosition(s, s1);
        assertEquals(new Position(1, 1), p);
    }

    @Test
    public void test_ship_overlap_position_2() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("r"), new Position(1, 1));
        Ship s1 = new AircraftCarrier(Direction.decode("r"), new Position(2, 1));
        Position p = Ship.overlapPosition(s, s1);
        assertNull(p);
    }

    @Test
    public void test_ship_1() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("r"), new Position(1, 1));
        assertEquals("Aircraft Carrier", s.getName());
        assertEquals(5, s.getLength());
    }

    @Test
    public void test_ship_2() throws InvalidInputException {
        Ship s = new Submarine(Direction.decode("r"), new Position(1, 1));
        assertEquals("Submarine", s.getName());
        assertEquals(3, s.getLength());
    }

    @Test
    public void test_ship_3() throws InvalidInputException {
        Ship s = new Submarine();
        assertEquals("Submarine", s.getName());
        assertEquals(3, s.getLength());
    }

    @Test
    public void test_ship_4() throws InvalidInputException {
        Ship s = new Destroyer(Direction.decode("r"), new Position(1, 1));
        assertEquals("Destroyer", s.getName());
        assertEquals(2, s.getLength());
    }

    @Test
    public void test_ship_5() throws InvalidInputException {
        Ship s = new Destroyer();
        assertEquals("Destroyer", s.getName());
        assertEquals(2, s.getLength());
    }

    @Test
    public void test_ship_6() throws InvalidInputException {
        Ship s = new Cruiser(Direction.decode("r"), new Position(1, 1));
        assertEquals("Cruiser", s.getName());
        assertEquals(3, s.getLength());
    }

    @Test
    public void test_ship_7() throws InvalidInputException {
        Ship s = new Cruiser();
        assertEquals("Cruiser", s.getName());
        assertEquals(3, s.getLength());
    }

    @Test
    public void test_ship_8() throws InvalidInputException {
        Ship s = new Battleship(Direction.decode("r"), new Position(1, 1));
        assertEquals("Battleship", s.getName());
        assertEquals(4, s.getLength());
    }

    @Test
    public void test_ship_9() throws InvalidInputException {

        Ship s = new Battleship();
        assertEquals("Battleship", s.getName());
        assertEquals(4, s.getLength());
    }
}
