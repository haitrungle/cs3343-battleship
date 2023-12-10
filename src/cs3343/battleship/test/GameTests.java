package cs3343.battleship.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Game;

public class GameTests {
    @BeforeEach
    public void setup() {
        new Config.Setter().withTypewriterDelay(0).set();
    }

    @AfterEach
    public void teardown() {
        Config.reset();
    }

    @Test
    public void runTutorialTwiceThenExit() {
        String input = "hai\n1\n" + "\n".repeat(95) + "1\n" + "\n".repeat(95) + "3\n";
        Console console = Console.make().withIn(input);
        Game game = new Game(console);
        game.run();
    }
}
