package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.exceptions.BackendException;
import cs3343.battleship.game.Console;

public class AskBackendTests {
    private Backend server;
    private Backend client;

    @BeforeEach
    public void setup() {
        server = null;
        client = null;
    }

    @AfterEach
    public void teardown() throws Exception {
        if (server != null)
            server.close();
        if (client != null)
            client.close();
    }

    @ParameterizedTest
    @ValueSource(strings = { "h1\ny\n", "uo\n123\n90\nY\n", "0\ntrue\nyes\n", "12.3\nzzz\nYES\n", "io\nYes\n" })
    public void invalidInput_shouldAskUntilYes(String input) throws Exception {
        Thread t = new Thread(() -> {
            try {
                Console console = Console.make().withIn(input);
                server = console.askBackend();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        assertNotNull(server);
    }

    @ParameterizedTest
    @ValueSource(strings = { "901\nyyy\nn\n\n", "p00\n849\n05\nN\n\n", "0\nfalse\nNo\n\n", "30.1\neo$$\nnO\n\n" })
    public void invalidInput_shouldAskUntilNo(String input) throws Exception {
        Thread serverThread = new Thread(() -> {
            try {
                Console console = Console.make().withIn("y\n");
                server = console.askBackend();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
        serverThread.join();

        Thread clientThread = new Thread(() -> {
            try {
                Console console = Console.make().withIn(input);
                client = console.askBackend();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        clientThread.start();
        clientThread.join();
        assertNotNull(server);
    }

    @Test
    public void noServer_clientConnectGivenAddress_shouldThrow() {
        String input = "n\n127.0.0.1:1234\n";
        Console console = Console.make().withIn(input);
        assertThrows(BackendException.class, () -> console.askBackend());
    }

    @Test
    public void noServer_clientConnectDefaultAddress_shouldThrow() {
        String input = "n\n\n";
        Console console = Console.make().withIn(input);
        assertThrows(BackendException.class, () -> console.askBackend());
    }

    @Test
    public void noServer_clientConnectNumber_shouldThrow() {
        String input = "n\n123412\n\n";
        Console console = Console.make().withIn(input);
        assertThrows(BackendException.class, () -> console.askBackend());
    }
}