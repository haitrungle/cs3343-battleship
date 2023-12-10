package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import cs3343.battleship.logic.AircraftCarrier;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Cruiser;
import cs3343.battleship.logic.Destroyer;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;
import cs3343.battleship.logic.Submarine;

public class ShipTests {
    private static final Direction down = Direction.DOWN;
    private static final Position startPos = new Position(2, -1);

    static Stream<Arguments> pairOfShipsProvider() {
        return Stream.of(
                Arguments.of(new AircraftCarrier(), new AircraftCarrier(down, startPos)),
                Arguments.of(new Battleship(), new Battleship(down, startPos)),
                Arguments.of(new Submarine(), new Submarine(down, startPos)),
                Arguments.of(new Cruiser(), new Cruiser(down, startPos)),
                Arguments.of(new Destroyer(), new Destroyer(down, startPos)));
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void sameType_shouldHaveSameNameAndLength(Ship a, Ship b) {
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getLength(), b.getLength());
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void getDirection_shouldBeCorrect(Ship a, Ship b) {
        assertEquals(down, b.getDirection());
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void getStartPosition_shouldBeCorrect(Ship a, Ship b) {
        assertEquals(startPos, b.getStartPosition());
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void setDirection_shouldBeCorrect(Ship a, Ship b) {
        Direction direction = Direction.RIGHT;
        a.setDirection(direction);
        b.setDirection(direction);
        assertEquals(direction, a.getDirection());
        assertEquals(direction, b.getDirection());
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void setStartPosition_shouldBeCorrect(Ship a, Ship b) {
        Position position = new Position(10, 10);
        a.setStartPosition(position);
        b.setStartPosition(position);
        assertEquals(position, a.getStartPosition());
        assertEquals(position, b.getStartPosition());
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void positionsOfShipDown(Ship a, Ship b) {
        List<Position> positions = b.positions();
        for (int i = 0; i < b.getLength(); i++) {
            assertEquals(new Position(startPos.row + i, startPos.col), positions.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void positionsOfShipRight(Ship a, Ship b) {
        b.setDirection(Direction.RIGHT);
        List<Position> positions = b.positions();
        for (int i = 0; i < b.getLength(); i++) {
            assertEquals(new Position(startPos.row, startPos.col + i), positions.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void overlapShip_shouldGiveOverlapPosition(Ship a, Ship b) {
        a.setDirection(Direction.RIGHT);
        a.setStartPosition(startPos);
        Position overlap = Ship.overlapPosition(a, b);
        assertEquals(startPos, overlap);
    }

    @ParameterizedTest
    @MethodSource("pairOfShipsProvider")
    public void nonOverlapShip_shouldGiveNull(Ship a, Ship b) {
        a.setDirection(Direction.DOWN);
        a.setStartPosition(new Position(startPos.row, startPos.col + 1));
        Position overlap = Ship.overlapPosition(a, b);
        assertNull(overlap);
    }
}
