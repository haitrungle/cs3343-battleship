package cs3343.battleship.test;

import org.junit.Before;

import cs3343.battleship.game.Console;
import cs3343.battleship.game.Match;

public class MatchTests {
    private Match server;

    private Match client;
    private Thread server_start;

    @Before
    public void setup() throws Exception {
        server_start = new Thread(() -> {
            try {
                String input = "y" + "\n".repeat(871);
                Console console = Console.withString(input);

                server = new Match(null, console);
                server.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        server_start.start();

        String input = "n" + "\n".repeat(871);
        Console console = Console.withString(input);
        client = new Match(null, console);
    }

    // @Test
    // public void test_battleship_match_1() throws Exception {
    //
    // client.run();
    // }
}
