package cs3343.battleship.test;

import com.sun.org.apache.bcel.internal.generic.FALOAD;
import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Player;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.AircraftCarrier;
import cs3343.battleship.logic.ship.Battleship;
import cs3343.battleship.logic.ship.Ship;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

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
        for(int i = 0; i < Config.BOARD_SIZE;i++){
            for(int j = 0; j < Config.BOARD_SIZE;j++){
                if(i==0 && j ==0)continue;

                p1.shotEnemy(new Position(i,j), false);
            }
        }
        Position p = console.askShot(p1);


        assertTrue(p.equals(new Position(0,0)));
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

        assertTrue(p.equals(new Position(0,0)));
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

        assertTrue(p.equals(new Position(0,0)));
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


        Exception e = assertThrows(Exception.class, ()->console.askShot(p1));
        assertEquals("For input string: \"a\"", e.getMessage());


    }
}
