package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.game.Tutorial;

public class TutorialTests {
    @BeforeEach
    public void setup() {
        new Config.Setter().withTypewriterDelay(0).set();
    }

    @AfterEach
    public void restoreStreams() {
    }

    @Test
    public void TutorialOutput() {
        String input = "r 4,1\nd 6,9\n\n\n\n3,4\n" + "\n".repeat(90);
        OutputStream output = new ByteArrayOutputStream();
        Console console = Console.make().withIn(input).withOut(output);
        Tutorial t = new Tutorial(console);
        t.run();
        String result = output.toString();
        assertTrue(result.contains("The enemy shot at 5,3. They have hit your ships!\nGAME OVER! You have won the game."));
    }
}
