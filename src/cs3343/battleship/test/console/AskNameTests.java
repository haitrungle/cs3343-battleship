package cs3343.battleship.test.console;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Console;

public class AskNameTests {
    @ParameterizedTest
    @ValueSource(strings = { "Kai12", "Henry32", "Hei63", "Hai85", "Kusuma17", "Le23", "aer129" })
    public void askNameKai_shouldGiveKai(String input) throws Exception {
        Scanner scanner = new Scanner(input).useDelimiter("[,\\s]+");
        Console console = new Console();
        console.setScanner(scanner);

        String result = Console.askName();
        assertEquals(input, result);
    }
}
