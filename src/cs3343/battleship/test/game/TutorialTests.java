package cs3343.battleship.test.game;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TutorialTests {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        Config.TYPEWRITER_EFFECT = false;
    }

    @After
    public void restoreStreams() {
        Config.TYPEWRITER_EFFECT = true;
    }

    @Test
    public void TutorialOutput() {
        String input = "r 4,1\nd 6,9\n\n\n\n3,4\n" + "\n".repeat(90);
        Console console = Console.make().withIn(input).withOut(output);
        Tutorial t = new Tutorial(console);
        t.run();
        String result = output.toString();
        assertEquals(189149, result.length());
        assertTrue(result.substring(188966, 189048)
                .equals("The enemy shot at 5,3. They have hit your ships!\nGAME OVER! You have won the game."));
    }
}
