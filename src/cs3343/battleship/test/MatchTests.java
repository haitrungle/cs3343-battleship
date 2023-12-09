package cs3343.battleship.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Message;
import cs3343.battleship.exceptions.BackendException;
import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Match;

public class MatchTests {
    private Match server;
    private Match client;
    private boolean serverResult;
    private boolean clientResult;

    @BeforeEach
    public void setup() {
        new Config.Setter().withTypewriterDelay(0).set();
        server = null;
        client = null;
    }

    @AfterEach
    public void teardown() throws Exception {
        if (server != null)
            server.getBackend().close();
        if (client != null)
            client.getBackend().close();
    }

    @Test
    public void runSuccessfully() throws Exception {
        Thread serverThread = new Thread(() -> {
            try {
                Backend backend = Console.make().withIn("y\n").askBackend();
                String input = "\n".repeat(100);
                Console console = Console.make().withIn(input);
                server = new Match(backend, console);
                serverResult = server.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();

        while (server == null) {
            Thread.sleep(50);
        }
        Backend backend = Console.make().withIn("n\n\n").askBackend();
        String input = "n" + "\n".repeat(100);
        Console console = Console.make().withIn(input);
        client = new Match(backend, console);
        clientResult = client.run();

        assertFalse(serverResult);
        assertTrue(clientResult);
    }
}
