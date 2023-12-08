package cs3343.battleship.test.game.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Client;
import cs3343.battleship.game.Console;

public class AskBackendTests {
    private final Backend[] server = new Backend[1];
    private Backend client;
    private Thread serverThread;

    @AfterEach
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
                Console console = Console.make().withIn(input);
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
        Console console = Console.make().withIn(input);
        Exception e = assertThrows(Exception.class, () -> console.askBackend());

        assertEquals("Error initializing Client: Connection refused", e.getMessage());
    }

    @Test
    public void noServer_clientConnectDefaultAddress_shouldThrow() throws Exception {
        String input = "n\n\n";
        Console console = Console.make().withIn(input);
        Exception e = assertThrows(Exception.class, () -> console.askBackend());

        assertEquals("Error initializing Client: Connection refused", e.getMessage());
    }

    @Test
    public void noServer_clientConnectNumber_shouldThrow() throws Exception {
        String input = "n\n123412\n\n";
        Console console = Console.make().withIn(input);
        Exception e = assertThrows(Exception.class, () -> console.askBackend());

        assertEquals("Error initializing Client: Connection refused", e.getMessage());
    }
}