package cs3343.battleship.test.game;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Player;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.Battleship;
import cs3343.battleship.logic.ship.Ship;

public class PlayerTests {
    private Player p1;

    @Before
    public void showBoard() {
        p1 = new Player();
        System.out.println(p1.boardToString());
    }

    @After
    public void showTwoBoards() {
        System.out.println(p1.twoBoardsToString());
    }

    @Test
    public void addShipDown_shouldHaveCorrectPositions() throws Exception {
        Ship s = new Battleship(Direction.DOWN, new Position(0, 0));
        p1.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertTrue(positions.get(i).equals(new Position(0 + i, 0)));
        }
    }

    @Test
    public void addShipRight_shouldHaveCorrectPositions() throws Exception {
        Ship s = new Battleship(Direction.RIGHT, new Position(0, 0));
        p1.addShip(s);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertTrue(positions.get(i).equals(new Position(0, 0 + i)));
        }
    }

    @Test
    public void addShipRandom_shouldHaveCorrectPositions() throws Exception {
        Ship s = new Battleship();
        p1.addShipRandom(s);
        List<Position> positions = s.positions();
        Direction d = s.getDirection();
        int row = positions.get(0).row;
        int col = positions.get(0).col;
        for (int i = 0; i < positions.size(); i++) {
            assertTrue(positions.get(i)
                    .equals(d == Direction.DOWN ? new Position(row + i, col) : new Position(row, col + i)));
        }
    }

    @Test
    public void getShotAtShipPositions_shouldBeTrue() throws Exception {
        Ship s = new Battleship();
        p1.addShipRandom(s);
        List<Position> positions = s.positions();

        for (int i = 0; i < positions.size(); i++) {
            assertTrue(p1.getShot(positions.get(i)));
        }
    }

    @Test
    public void getShotAtEmptyBoard_shouldBeFalse() throws Exception {
        for (int i = 0; i < Config.BOARD_SIZE; i++) {
            for (int j = 0; j < Config.BOARD_SIZE; j++) {
                assertFalse(p1.getShot(new Position(i, j)));
            }
        }
    }

    @Test
    public void shotEnemyTwice_shouldThrow() throws Exception {
        Position p = new Position(0, 0);
        p1.shotEnemy(p, false);
        Exception e = assertThrows(Exception.class, () -> p1.shotEnemy(new Position(0, 0), false));
        assertEquals(Console.colorize("Invalid input: ", Console.Color.RED) + "You have already shot at " + p
                + ". Please select another position.", e.getMessage());
    }

    @Test
    public void checkShotAtShotPositionsShouldThrow() throws Exception {
        Position p = new Position(0, 0);
        p1.shotEnemy(p, false);
        Exception e = assertThrows(Exception.class, () -> p1.checkShot(new Position(0, 0)));
        assertEquals(Console.colorize("Invalid input: ", Console.Color.RED) + "You have already shot at " + p
                + ". Please select another position.", e.getMessage());
    }

    @Test
    public void negativeNumberForPosition_shouldThrow() throws Exception {
        Position p = new Position(-1, 0);
        Exception e = assertThrows(Exception.class, () -> p1.checkShot(p));
        assertEquals(
                Console.colorize("Invalid input: ", Console.Color.RED) + "Position (" + "-1" + "," + 0
                        + ") is out of bounds. Row and column must be between 0 and " + (Config.BOARD_SIZE - 1) + ".",
                e.getMessage());
    }

    @Test
    public void afterAddShip_shouldHaveAliveShip() throws Exception {
        Ship s = new Battleship();
        p1.addShipRandom(s);

        assertTrue(p1.hasAliveShip());
    }

    @Test
    public void default_shouldHaveNoShip() throws Exception {
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
        Position p = p1.getRandomShot();
        p1.shotEnemy(p, true);
        boolean b = p1.hasShotEnemyAt(p);
        assertTrue(b);
    }

    @Test
    public void shouldRememberLocationNotShot() throws Exception {
        Position p = p1.getRandomShot();
        boolean b = p1.hasShotEnemyAt(p);
        assertFalse(b);
    }
}
