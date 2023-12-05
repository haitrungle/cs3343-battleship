package cs3343.battleship.test;

import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Test;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Player;
import cs3343.battleship.logic.Position;

public class AskShotTests {
    @Test
    public void allPositionsExcept00AreShot_askShotRandom_shouldGive00() throws Exception {
        String input = "\n";
        Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Player p1 = new Player();
        for (int i = 0; i < Config.BOARD_SIZE; i++) {
            for (int j = 0; j < Config.BOARD_SIZE; j++) {
                if (i == 0 && j == 0)
                    continue;

                p1.shotEnemy(new Position(i, j), false);
            }
        }
        Position p = console.askShot(p1);

        assertTrue(p.equals(new Position(0, 0)));
    }

    @Test
    public void askShotTwoNumbers_shouldGivePosition() throws Exception {
        String input = "0 0\n";
        Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Player p1 = new Player();

        Position p = console.askShot(p1);

        assertTrue(p.equals(new Position(0, 0)));
    }

    @Test
    public void askShotOneNumber_shouldAskUntilCorrectPosition() throws Exception {
        String input = "0 \n0 0";
        Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Player p1 = new Player();

        Position p = console.askShot(p1);

        assertTrue(p.equals(new Position(0, 0)));
    }

    @Test
    public void askShotLetters_shouldAskUntilCorrectPosition() throws Exception {
        String input = "a b\n0 0";
        Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Player p1 = new Player();
        Position p = console.askShot(p1);

        assertTrue(p.equals(new Position(0, 0)));
    }
}
