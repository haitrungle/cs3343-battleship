package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Console;

public class AskGameOptionTests {
    @ParameterizedTest
    @ValueSource(strings = {
            "4\n12\n321\n3\n",
            "-4\n1.0\n2e-1\n3\n",
    })
    public void invalidNumber_shouldAskUntilCorrect(String input) {
        Console console = Console.make().withIn(input);
        int option = console.askGameOption();
        assertEquals(3, option);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "x\nceo02\n123m1\n1\n",
            "o56\ncmco\n9vvb\n1\n",
    })
    public void notNumber_shouldAskUntilCorrect(String input) {
        Console console = Console.make().withIn(input);
        int option = console.askGameOption();
        assertEquals(1, option);
    }

    @ParameterizedTest
    @ValueSource(strings = { "1\n", "2\n", "3\n" })
    public void validNumber_shouldGiveCorrectNumber(String input) {
        Console console = Console.make().withIn(input);
        int a = console.askGameOption();
        assertEquals(input.charAt(0) - '0', a);
    }
}
