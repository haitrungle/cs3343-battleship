package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;

import org.junit.jupiter.api.Test;

import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Direction;

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
        Position pos1 = Position.random(new Random(12), 3);
        Position pos2 = Position.random(new Random(12), 3);
        assertEquals(pos1, pos2);
    }

    @Test
    public void notEqualToDirection() {
        Position pos = Position.random(new Random(), 1);
        assertNotEquals(Direction.random(new Random()), pos);
    }
}
