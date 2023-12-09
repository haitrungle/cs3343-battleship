package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.Test;

import cs3343.battleship.exceptions.InvalidInputException;
import cs3343.battleship.logic.Direction;

public class DirectionTests {
    @Test
    public void test_direction_down_1() throws Exception {
        Direction dir = Direction.decode("D");
        assertEquals(Direction.DOWN, dir);
    }

    @Test
    public void test_direction_down_2() throws Exception {
        Direction dir = Direction.decode("d");
        assertEquals(Direction.DOWN, dir);
    }

    @Test
    public void test_direction_down_3() throws Exception {
        Direction dir = Direction.decode("down");
        assertEquals(Direction.DOWN, dir);
    }

    @Test
    public void test_direction_right_1() throws Exception {
        Direction dir = Direction.decode("R");
        assertEquals(Direction.RIGHT, dir);
    }

    @Test
    public void test_direction_right_2() throws Exception {
        Direction dir = Direction.decode("r");
        assertEquals(Direction.RIGHT, dir);
    }

    @Test
    public void test_direction_right_3() throws Exception {
        Direction dir = Direction.decode("right");
        assertEquals(Direction.RIGHT, dir);
    }

    @Test
    public void test_direction_exception_1() {
        assertThrows(InvalidInputException.class, () -> Direction.decode("other"));
    }

    @Test
    public void test_direction_random() {
        Direction d = Direction.random(new Random());
        assertEquals(d, d);
    }
}
