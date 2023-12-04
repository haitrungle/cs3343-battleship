package cs3343.battleship.test;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Player;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.Battleship;
import cs3343.battleship.logic.ship.Ship;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class test_battleship_game_console_ask_and_add_ship {


    @Test
    public void test_battleship_game_console_ask_and_add_ship_1() throws Exception {
        String input = "\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Ship ship = new Battleship();
        Player p1 = new Player();
        Ship s = console.askAndAddShip(ship,p1);

        assertTrue(s.getStartPosition().equals(ship.getStartPosition()));

    }
    @Test
    public void test_battleship_game_console_ask_and_add_ship_2() throws Exception {
        String input = "d 0 0\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Ship ship = new Battleship();
        Player p1 = new Player();
        Ship s = console.askAndAddShip(ship,p1);
        List<Position> positions = s.positions();
        for(int i = 0; i < positions.size();i++){
            assertTrue(positions.get(i).equals(new Position(0+i,0)));
        }

    }
    @Test
    public void test_battleship_game_console_ask_and_add_ship_3() throws Exception {
        String input = "d 0 0 1\nd 0 0";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Ship ship = new Battleship();
        Player p1 = new Player();

        Ship s = console.askAndAddShip(ship,p1);
        List<Position> positions = s.positions();
        for(int i = 0; i < positions.size();i++){
            assertTrue(positions.get(i).equals(new Position(0+i,0)));
        }
    }
    @Test
    public void test_battleship_game_console_ask_and_add_ship_4() throws Exception {
        String input = "d 0\nd 0 0";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Ship ship = new Battleship();
        Player p1 = new Player();

        Ship s = console.askAndAddShip(ship,p1);
        List<Position> positions = s.positions();
        for(int i = 0; i < positions.size();i++){
            assertTrue(positions.get(i).equals(new Position(0+i,0)));
        }
    }

}
