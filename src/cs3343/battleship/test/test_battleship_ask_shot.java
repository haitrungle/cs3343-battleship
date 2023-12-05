package cs3343.battleship.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Player;
import cs3343.battleship.logic.Position;

public class test_battleship_ask_shot {
    @Test
    public void test_battleship_ask_shot_1() throws Exception {
        String input = "\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
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
    public void test_battleship_ask_shot_2() throws Exception {
        String input = "0 0\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Player p1 = new Player();

        Position p = console.askShot(p1);

        assertTrue(p.equals(new Position(0, 0)));
    }

    @Test
    public void test_battleship_ask_shot_3() throws Exception {
        String input = "0 \n0 0";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Player p1 = new Player();

        Position p = console.askShot(p1);

        assertTrue(p.equals(new Position(0, 0)));
    }

    @Test
    public void test_battleship_ask_shot_4() throws Exception {
        String input = "a b\n0 0";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Player p1 = new Player();

        Exception e = assertThrows(Exception.class, () -> console.askShot(p1));
        assertEquals("For input string: \"a\"", e.getMessage());
    }
}
