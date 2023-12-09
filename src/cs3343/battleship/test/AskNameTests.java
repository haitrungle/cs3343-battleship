package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Console;

public class AskNameTests {
    @ParameterizedTest
    @ValueSource(strings = { "Kai 12\n", "Henry 32\n", "Hei 63\n", "Hai 85\n", "Kusuma 17\n", "Le 23\n", "aer 129" })
    public void string_shouldGiveString(String input) {
        Console console = Console.make().withIn(input);
        String result = console.askName();
        assertEquals(input.trim(), result);
    }
}
