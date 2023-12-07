package cs3343.battleship.test.logic;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.Direction;

public class PositionTests {
    @Test
    public void test_position_to_string_1() {
        Position pos = new Position(4, 5);
        assertEquals("4,5", pos.toString());
    }

    @Test
    public void test_position_to_string_2() {
        Position pos = Position.random(new Random(), 1);
        assertEquals("0,0", pos.toString());
    }

    @Test
    public void test_position_equals_1() {
        Position pos = Position.random(new Random(), 1);
        boolean b = pos.equals(null);
        assertEquals(false, b);
    }

    @Test
    public void test_position_equals_2() {
        Position pos = Position.random(new Random(), 1);
        boolean b = pos.equals(Direction.random(new Random()));
        assertEquals(false, b);
    }

    @Test
    public void test_position_equals_3() {
        Position pos = Position.random(new Random(), 1);
        boolean b = pos.equals(Position.random(new Random(), 1));
        assertEquals(true, b);
    }
}
