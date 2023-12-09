package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Console;

public class AskNameTests {
    @ParameterizedTest
    @ValueSource(strings = { "Kai12", "Henry32", "Hei63", "Hai85", "Kusuma17", "Le23", "aer129" })
    public void askNameKai_shouldGiveKai(String input) {
        Console console = Console.make().withIn(input);

        String result = console.askName();
        assertEquals(input, result);
    }
}
