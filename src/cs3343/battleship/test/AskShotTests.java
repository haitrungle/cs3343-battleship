package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.logic.Player;
import cs3343.battleship.logic.Position;

public class AskShotTests {
    private static final int boardSize = Config.DEFAULT_BOARD_SIZE;

    @Test
    public void randomShot_ifAllPositionsExcept00AreShot_shouldGive00() throws Exception {
        String input = "\n";
        Console console = Console.make().withIn(input);

        Player p1 = new Player(boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i == 0 && j == 0)
                    continue;

                p1.shotEnemy(new Position(i, j), false);
            }
        }
        Position p = console.askShot(p1);

        assertEquals(new Position(0, 0), p);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0, 0\n",
            "0,0\n",
            "0 0\n",
            "0 , 0\n",
    })
    public void validInput_shouldGiveCorrectPosition() {
        String input = "0 0\n";
        Console console = Console.make().withIn(input);
        Player p1 = new Player(boardSize);
        Position p = console.askShot(p1);
        assertEquals(new Position(0, 0), p);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1 1x\n4 of 0\n40g\n0,0\n",
            "-1 1\n1 -1\n200 200\n0 0\n",
            "123\n-49\n0 0\n",
            "iodine\ncaleier 1 9\n0 0\n",
    })
    public void invalidInput_shouldAskUntilCorrectPosition(String input) {
        Console console = Console.make().withIn(input);
        Player p1 = new Player(boardSize);
        Position p = console.askShot(p1);
        assertEquals(new Position(0, 0), p);
    }
}
