package cs3343.battleship.test.game.console;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import cs3343.battleship.game.Console;

public class AskGameOptionTests {
    @Test
    public void numberNot1Or2_shouldAskUntilCorrect() throws Exception {
        String input = "3\n1";
        Console console = Console.withString(input);

        int option = console.askGameOption();

        assertEquals(1, option);
    }

    @Test
    public void notNumber_shouldAskUntilCorrect() throws Exception {
        String input = "x\n1";
        Console console = Console.withString(input);

        int option = console.askGameOption();

        assertEquals(1, option);
    }

    @Test
    public void number1_shouldGive1() throws Exception {
        String input = "1\n";
        Console console = Console.withString(input);

        int a = console.askGameOption();

        assertEquals(1, a);
    }

    @Test
    public void number2_shouldGive2() throws Exception {
        String input = "2\n";
        Console console = Console.withString(input);

        int a = console.askGameOption();

        assertEquals(2, a);
    }
}
