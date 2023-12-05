package cs3343.battleship.test;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Client;
import cs3343.battleship.backend.Server;
import cs3343.battleship.backend.SocketBackend;
import cs3343.battleship.game.Console;

import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class AskBackendTests {
    private final Backend[] server = new Backend[1];
    private Backend client;
    private Thread serverThread;

    @After
    public void teardown() throws Exception {
        if (serverThread != null) serverThread.join();
        if (server[0] != null) server[0].close();
        if (client != null) client.close();

        Thread.sleep(100);
    }

    @Test
    public void askIsServerInvalidInput_shouldAskUntilCorrect() throws Exception {
        String input = "h\ny";
        serverThread = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
                Console console = new Console();
                console.setScanner(scanner);
                server[0] = console.askBackend();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
        // To ensure server has started
        Thread.sleep(500);
        client = new Client("localhost", 1234);
        client.sendMessage(null);
    }

    @Test
    public void noServer_clientConnectGivenAddress_shouldThrow() throws Exception {
        String input = "n\n127.0.0.1:1234\n";
        Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);
        Exception e = assertThrows(Exception.class, () -> console.askBackend());

        assertEquals("Error initializing Client: Connection refused", e.getMessage());
    }

    @Test
    public void noServer_clientConnectDefaultAddress_shouldThrow() throws Exception {
        String input = "n\n\n";
        Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);
        Exception e = assertThrows(Exception.class, () -> console.askBackend());

        assertEquals("Error initializing Client: Connection refused", e.getMessage());
    }

    @Test
    public void noServer_clientConnectNumber_shouldThrow() throws Exception {
        String input = "n\n123412\n\n";
        Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);
        Exception e = assertThrows(Exception.class, () -> console.askBackend());

        assertEquals("Error initializing Client: Connection refused", e.getMessage());
    }
}