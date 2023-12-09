package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs3343.battleship.game.Config;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Player;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;

public class PlayerTests {
    private Player p1;

    @BeforeEach
    public void showBoard() {
        p1 = new Player(Config.getBoardSize());
    }

    @Test
    public void addShipDown_shouldHaveCorrectPositions() throws Exception {
        Ship s = new Battleship(Direction.DOWN, new Position(0, 0));
        p1.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertEquals(new Position(0 + i, 0), positions.get(i));
        }
    }

    @Test
    public void addShipRight_shouldHaveCorrectPositions() throws Exception {
        Ship s = new Battleship(Direction.RIGHT, new Position(0, 0));
        p1.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertEquals(new Position(0, 0 + i), positions.get(i));
        }
    }

    @Test
    public void addShipRandom_shouldHaveCorrectPositions() {
        Ship s = new Battleship();
        p1.addShipRandom(s, Config.rng());
        List<Position> positions = s.positions();
        Direction d = s.getDirection();
        int row = positions.get(0).row;
        int col = positions.get(0).col;
        for (int i = 0; i < positions.size(); i++) {
            Position expected = d == Direction.DOWN ? new Position(row + i, col) : new Position(row, col + i);
            assertEquals(expected, positions.get(i));
        }
    }

    @Test
    public void getShotAtShipPositions_shouldBeTrue() throws Exception {
        Ship s = new Battleship();
        p1.addShipRandom(s, Config.rng());
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertTrue(p1.getShot(positions.get(i)));
        }
    }

    @Test
    public void getShotAtEmptyBoard_shouldBeFalse() throws Exception {
        for (int i = 0; i < p1.getBoardSize(); i++) {
            for (int j = 0; j < p1.getBoardSize(); j++) {
                assertFalse(p1.getShot(new Position(i, j)));
            }
        }
    }

    @Test
    public void shotEnemyTwice_shouldThrow() throws Exception {
        Position p = new Position(0, 0);
        p1.shotEnemy(p, false);
        Exception e = assertThrows(Exception.class, () -> p1.shotEnemy(new Position(0, 0), false));
    }

    @Test
    public void checkShotAtShotPositionsShouldThrow() throws Exception {
        Position p = new Position(0, 0);
        p1.shotEnemy(p, false);
        assertThrows(Exception.class, () -> p1.checkShot(new Position(0, 0)));
    }

    @Test
    public void negativeNumberForPosition_shouldThrow() {
        Position p = new Position(-1, 0);
        assertThrows(Exception.class, () -> p1.checkShot(p));
    }

    @Test
    public void afterAddShip_shouldHaveAliveShip() {
        Ship s = new Battleship();
        p1.addShipRandom(s, Config.rng());
        assertTrue(p1.hasAliveShip());
    }

    @Test
    public void default_shouldHaveNoShip() {
        assertFalse(p1.hasAliveShip());
    }

    @Test
    public void shipWithNoOverlap() throws Exception {
        Ship s = new Battleship(Direction.DOWN, new Position(0, 0));
        p1.addShip(s);
        Ship s1 = new Battleship(Direction.DOWN, new Position(0, 1));
        assertNull(p1.hasOverlapShip(s1));
    }

    @Test
    public void shipWithOverlap() throws Exception {
        Ship s = new Battleship(Direction.DOWN, new Position(0, 0));
        p1.addShip(s);
        Ship s1 = new Battleship(Direction.DOWN, new Position(0, 0));
        assertEquals(new Position(0, 0), p1.hasOverlapShip(s1));
    }

    @Test
    public void shouldRememberLocationShot() throws Exception {
        Position p = p1.getRandomShot(Config.rng());
        p1.shotEnemy(p, true);
        boolean b = p1.hasShotEnemyAt(p);
        assertTrue(b);
    }

    @Test
    public void shouldRememberLocationNotShot() {
        Position p = p1.getRandomShot(Config.rng());
        boolean b = p1.hasShotEnemyAt(p);
        assertFalse(b);
    }
}
