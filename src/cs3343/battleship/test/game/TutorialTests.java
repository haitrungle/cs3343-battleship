package cs3343.battleship.test.game;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TutorialTests {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        Config.TYPEWRITER_DELAY = 0;
    }

    @AfterEach
    public void restoreStreams() {
        Config.TYPEWRITER_DELAY = 75;
    }

    @Test
    public void TutorialOutput() {
        String input = "r 4,1\nd 6,9\n\n\n\n3,4\n" + "\n".repeat(90);
        Console console = Console.make().withIn(input).withOut(output);
        Tutorial t = new Tutorial(console);
        t.run();
        String result = output.toString();
        assertEquals(189149, result.length());
        assertEquals("The enemy shot at 5,3. They have hit your ships!\nGAME OVER! You have won the game.", result.substring(188966, 189048));
    }
}
