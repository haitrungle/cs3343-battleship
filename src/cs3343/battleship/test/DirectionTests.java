package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.exceptions.InvalidInputException;
import cs3343.battleship.logic.Direction;

public class DirectionTests {
    @ParameterizedTest
    @ValueSource(strings = { "d", "D", "down", "Down", "DOWN", "doWN", "dOWn", "dowN" })
    public void down_shouldDecodeCorrectly(String input) throws Exception {
        Direction dir = Direction.decode(input);
        assertEquals(Direction.DOWN, dir);
    }

    @ParameterizedTest
    @ValueSource(strings = { "r", "R", "right", "Right", "RIGHT", "riGHT", "rIGHT", "riGhT" })
    public void right_shouldDecodeCorrectly(String input) throws Exception {
        Direction dir = Direction.decode(input);
        assertEquals(Direction.RIGHT, dir);
    }

    @ParameterizedTest
    @ValueSource(strings = { "other", "ceh", "righ", "dow", "up", "left", "downn", "rightt" })
    public void invalidInput_shouldThrow(String input) {
        assertThrows(InvalidInputException.class, () -> Direction.decode(input));
    }

    @Test
    public void equalsTest() {
        Direction d = Direction.random(new Random());
        assertEquals(d, d);
    }
}
