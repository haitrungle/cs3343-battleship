package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Console;
import cs3343.battleship.logic.Battleship;
import cs3343.battleship.logic.Player;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;

public class AskAndAddShipTests {
    @Test
    public void random_shouldMutateShip() {
        String input = "\n";
        Console console = Console.make().withIn(input);
        Ship ship = new Battleship();
        Player p1 = new Player(10);
        Ship s = console.askAndAddShip(ship, p1);
        assertEquals(ship, s);
    }

    @Test
    public void d00_shouldGiveCorrectPositions() {
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

    @ParameterizedTest
    @ValueSource(strings = {
            "der 2 2\nd 0 0\n",
            "red 2 2\nxya 2 2\nd 0 0\n",
            "xya 2 2\ntest 3 3\nd 0 0\n",
    })
    public void invalidDirection_shouldAskUntilCorrect(String input) {
        Console console = Console.make().withIn(input);
        Ship ship = new Battleship();
        Player p1 = new Player(10);
        Ship s = console.askAndAddShip(ship, p1);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertEquals(new Position(0 + i, 0), positions.get(i));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "d 200\nd 2 0 0\nr 0 0\n",
            "r xoi\nr 00\nr00\nr 0 0\n",
            "r -1 1\nr 20 20\nr 0 0\n",
    })
    public void invalidPosition_shouldAskUntilCorrect(String input) {
        Console console = Console.make().withIn(input);
        Ship ship = new Battleship();
        Player p1 = new Player(10);
        Ship s = console.askAndAddShip(ship, p1);
        List<Position> positions = s.positions();
        for (int i = 0; i < positions.size(); i++) {
            assertEquals(new Position(0, 0 + i), positions.get(i));
        }
    }
}
