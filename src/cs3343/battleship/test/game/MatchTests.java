package cs3343.battleship.test.game;

import java.io.ByteArrayOutputStream;

import org.junit.Before;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Match;

public class MatchTests {
    private Match server;
    private Match client;

    private final ByteArrayOutputStream serverOutput = new ByteArrayOutputStream();
    private final ByteArrayOutputStream clientOutput = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        Config.TYPEWRITER_EFFECT = false;
    }

    @Before
    public void setup() throws Exception {
        Thread serverThread = new Thread(() -> {
            try {
                String input = "y" + "\n".repeat(95);
                Console console = Console.make().withIn(input).withOut(serverOutput);
                server = new Match(null, console);
                server.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();

        // Ensure that the server has started
        Thread.sleep(1000);
        String input = "n" + "\n".repeat(95);
        Console console = Console.make().withIn(input).withOut(clientOutput);
        client = new Match(null, console);
        client.run();
    }
}
