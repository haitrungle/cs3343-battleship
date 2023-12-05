package cs3343.battleship.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs3343.battleship.game.Console;

public class test_battleship_game_console {
    private InputStream sysInBackup;
    private PrintStream sysOutBackup;

    @Before
    public void setUpStreams() {
        Console.typeln("Testing");
        sysInBackup = System.in; // Backup System.in to restore it later
        sysOutBackup = System.out; // Backup System.out to restore it later
    }

    @After
    public void restoreStreams() {
        // Restore System.in and System.out
        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);
    }

    @Test
    public void test_console_ask_name() throws Exception {
        String input = "KAI";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        String result = Console.askName();
        assertEquals(input, result);
    }

    @Test
    public void test_console_ask_name_1() throws Exception {
        String input = "Henry";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        System.setIn(inputStream);
        String result = console.askName();

        assertEquals(input, result);
    }
}
