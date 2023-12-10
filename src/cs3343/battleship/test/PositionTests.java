package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;

public class PositionTests {
    @Test
    public void shouldHaveCorrectStringRep() {
        Position pos = new Position(4, 5);
        assertEquals("4,5", pos.toString());
    }

    @Test
    public void randomPositionInBoardOfSize1_shouldBe00() {
        Position pos = Position.random(new Random(), 1);
        assertEquals("0,0", pos.toString());
    }

    @Test
    public void randomPosition_shouldNotBeNull() {
        Position pos = Position.random(new Random(), 10);
        assertNotNull(pos);
    }

    @Test
    public void randomPositions_withSameSeed_ShouldBeSame() {
        Position pos1 = Position.random(new Random(12), 5);
        Position pos2 = Position.random(new Random(12), 5);
        assertEquals(pos1, pos2);
    }

    static Stream<Arguments> numberPairProvider() {
        return Stream.of(
            Arguments.of(0, 0),
            Arguments.of(1, 2),
            Arguments.of(-1, -2),
            Arguments.of(3, 4),
            Arguments.of(50, 100),
            Arguments.of(100, 50)
        );
    }

    @ParameterizedTest
    @MethodSource("numberPairProvider")
    public void equalPositions_shouldEqual(int row, int col) {
        Position pos1 = new Position(row, col);
        Position pos2 = new Position(row, col);
        assertEquals(pos1, pos2);
    }

    @ParameterizedTest
    @MethodSource("numberPairProvider")
    public void unequalPositions_shouldNotEqual(int row, int col) {
        Position pos1 = new Position(row, col);
        Position pos2 = new Position(row + 1, col);
        assertNotEquals(pos1, pos2);
    }

    @Test
    public void shouldNotEqualToDirection() {
        Position pos = Position.random(new Random(), 1);
        assertNotEquals(Direction.random(new Random()), pos);
    }
}
