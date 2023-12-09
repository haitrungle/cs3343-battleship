package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cs3343.battleship.game.Console;

public class AskGameOptionTests {
    @Test
    public void numberNotValid_shouldAskUntilCorrect() {
        String input = "4\n12\n321\n3\n";
        Console console = Console.make().withIn(input);
        int option = console.askGameOption();
        assertEquals(3, option);
    }

    @Test
    public void notNumber_shouldAskUntilCorrect() {
        String input = "x\nceo02\n123m1\n1";
        Console console = Console.make().withIn(input);
        int option = console.askGameOption();
        assertEquals(1, option);
    }

    @Test
    public void number1_shouldGive1() {
        String input = "1\n";
        Console console = Console.make().withIn(input);
        int a = console.askGameOption();
        assertEquals(1, a);
    }

    @Test
    public void number2_shouldGive2() {
        String input = "2\n";
        Console console = Console.make().withIn(input);
        int a = console.askGameOption();
        assertEquals(2, a);
    }
}
