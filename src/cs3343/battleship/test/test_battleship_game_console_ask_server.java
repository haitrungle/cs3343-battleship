package cs3343.battleship.test;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Client;
import cs3343.battleship.game.Console;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class test_battleship_game_console_ask_server {

    Backend server;

    private Thread server_start;



    @Test
    public void test_console_ask_is_server() throws Exception {
        String input = "y";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        server_start = new Thread(()->{
            try {
                Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
                Console console = new Console();
                console.setScanner(scanner);
                server = console.askBackend();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        server_start.start();

        Backend client = new Client("localhost",1234);
        client.sendMessage(null);
        client.close();
        server.close();

    }

    @Test
    public void test_console_ask_is_server_1() throws Exception {
        String input = "h";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);
        Exception e = assertThrows(Exception.class, ()-> console.askBackend());

        assertEquals(Console.textColor("Invalid input: ", Console.RED)+ "Can only be yes or no. Please enter 'y'/'yes' or 'n'/'no'.", e.getMessage());

    }

    @Test
    public void test_console_ask_is_server_2() throws Exception {
        String input = "n\n127.0.0.1:1234\n";
        //String expectedOutput = "Are you the server? [y/n]\n> Enter the address of server, e.g. '127.0.0.1:1234'\n> ";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);



        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);
        Exception e = assertThrows(Exception.class, ()-> console.askBackend());

        assertEquals("Error initializing Client: Connection refused: connect", e.getMessage());




    }
    @Test
    public void test_console_ask_is_server_3() throws Exception {
        String input = "n\n\n";
        //String expectedOutput = "Are you the server? [y/n]\n> Enter the address of server, e.g. '127.0.0.1:1234'\n> ";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);



        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);
        Exception e = assertThrows(Exception.class, ()-> console.askBackend());

        assertEquals("Error initializing Client: Connection refused: connect", e.getMessage());

    }

    @Test
    public void test_console_ask_is_server_4() throws Exception {
        String input = "n\n123412\n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Exception e = assertThrows(Exception.class, ()-> console.askBackend());

        assertEquals("Error initializing Client: Connection refused: connect", e.getMessage());

    }

    @Test
    public void test_console_ask_is_server_5() throws Exception {
        String input = "3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Exception e = assertThrows(Exception.class, ()-> console.askGameOption());

        assertEquals(Console.textColor("Invalid input: ", Console.RED)+"Can only be 1 or 2. Please enter 1 or 2.", e.getMessage());

    }

    @Test
    public void test_console_ask_is_server_6() throws Exception {
        String input = "x\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        Exception e = assertThrows(Exception.class, ()-> console.askGameOption());

        assertEquals("Cannot parse integer. Please enter 1 or 2.", e.getMessage());

    }

    @Test
    public void test_console_ask_is_server_7() throws Exception {
        String input = "1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        int a = console.askGameOption();

        assertEquals(1,a);

    }

    @Test
    public void test_console_ask_is_server_8() throws Exception {
        String input = "2\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        System.setIn(inputStream);

        Scanner scanner = new Scanner(System.in).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        int a = console.askGameOption();

        assertEquals(2,a);

    }


}
