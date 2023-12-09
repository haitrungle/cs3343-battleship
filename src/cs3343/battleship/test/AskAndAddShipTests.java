package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import cs3343.battleship.game.Console;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Player;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;

public class AskAndAddShipTests {
    @Test
    public void askAndAddShipRandom_shouldMutateShip() {
        String input = "\n";
        Console console = Console.make().withIn(input);
        Ship ship = new Battleship();
        Player p1 = new Player(10);
        Ship s = console.askAndAddShip(ship, p1);
        assertEquals(ship, s);
    }

    @Test
    public void askAndAddShipD00_shouldGiveCorrectPositions() {
        String input = "d 0 0\n";
        Console console = Console.make().withIn(input);
        Ship ship = new Battleship();
        Player p1 = new Player(10);
        Ship s = console.askAndAddShip(ship, p1);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertEquals(new Position(0 + i, 0), positions.get(i));
        }
    }

    @Test
    public void askAndAddShipExtraNumber_shouldGiveCorrectPositions() {
        String input = "d 0 0 1\nd 0 0";
        Console console = Console.make().withIn(input);
        Ship ship = new Battleship();
        Player p1 = new Player(10);
        Ship s = console.askAndAddShip(ship, p1);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertEquals(new Position(0 + i, 0), positions.get(i));
        }
    }

    @Test
    public void askAndAddShipMissingNumber_shouldGiveCorrectPositions() {
        String input = "d 0\nd 0 0";
        Console console = Console.make().withIn(input);
        Ship ship = new Battleship();
        Player p1 = new Player(10);
        Ship s = console.askAndAddShip(ship, p1);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertEquals(new Position(0 + i, 0), positions.get(i));
        }
    }
}
