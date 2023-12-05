package cs3343.battleship.test.game.console;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Console;

public class AskNameTests {
    @ParameterizedTest
    @ValueSource(strings = { "Kai12", "Henry32", "Hei63", "Hai85", "Kusuma17", "Le23", "aer129" })
    public void askNameKai_shouldGiveKai(String input) throws Exception {
        Console console = Console.withString(input);

        String result = console.askName();
        assertEquals(input, result);
    }
}
