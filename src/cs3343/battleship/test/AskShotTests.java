package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Config;
import cs3343.battleship.game.Console;
import cs3343.battleship.logic.Player;
import cs3343.battleship.logic.Position;

public class AskShotTests {
    private static final int boardSize = Config.DEFAULT_BOARD_SIZE;

    static Stream<Position> positionProvider() {
        return Stream.of(
                new Position(1, 2),
                new Position(2, 1),
                new Position(4, 5),
                new Position(3, 6),
                new Position(5, 0),
                new Position(0, 5));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    public void ifAllPositionsExceptOneAreShot_randomShouldGiveThatPosition(Position pos) throws Exception {
        String input = "\n";
        Console console = Console.make().withIn(input);

        Player p1 = new Player(boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i == pos.row && j == pos.col)
                    continue;
                p1.shotEnemy(new Position(i, j), false);
            }
        }
        Position shot = console.askShot(p1);

        assertEquals(pos, shot);
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
