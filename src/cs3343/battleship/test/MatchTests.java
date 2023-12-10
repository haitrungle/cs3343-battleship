package cs3343.battleship.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.exceptions.GameException;
import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Match;

public class MatchTests {
    private Match server;
    private Match client;
    private boolean serverResult;
    private boolean clientResult;
    private Thread serverThread;

    @BeforeEach
    public void setup() {
        new Config.Setter().withTypewriterDelay(0).set();
        server = null;
        client = null;
        serverThread = null;
    }

    @AfterEach
    public void teardown() throws Exception {
        Config.reset();
        if (server != null)
            server.getBackend().close();
        if (client != null)
            client.getBackend().close();
        if (serverThread != null)
            serverThread.interrupt();
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
                System.out.println(e);
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

    @Test
    public void abortDuringMatch() throws Exception {
        serverThread = new Thread(() -> {
            try {
                Backend backend = Console.make().withIn("y\n").askBackend();
                String input = "\n".repeat(100);
                Console console = Console.make().withIn(input);
                server = new Match(backend, console);
                serverResult = server.run();
            } catch (Exception e) {
                System.out.println("server: " + e);
            }
        });
        serverThread.start();

        while (server == null) {
            Thread.sleep(50);
        }
        Backend backend = Console.make().withIn("n\n\n").askBackend();
        String input = "\n".repeat(100);
        Console console = Console.make().withIn(input);
        client = new Match(backend, console);
        new Thread(() -> {
            try {
                Thread.sleep(300);
                backend.close();
            } catch (Exception e) {
                System.out.println("client: " + e);
            }
        }).start();
        assertThrows(GameException.class, () -> client.run());
    }
}
