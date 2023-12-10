package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import cs3343.battleship.game.Config;

public class ConfigTests {
    @Test
    public void defaultBoardSizeShouldBeCorrect() {
        assertEquals(Config.DEFAULT_BOARD_SIZE, Config.getBoardSize());
    }

    @Test
    public void defaultServerPortShouldBeCorrect() {
        assertEquals(Config.DEFAULT_SERVER_PORT, Config.getServerPort());
    }

    @Test
    public void defaultTypewriterDelayShouldBeCorrect() {
        assertEquals(Config.DEFAULT_TYPEWRITER_DELAY, Config.getTypewriterDelay());
    }

    @Test
    public void defaultFleetShouldHaveLength5() {
        assertEquals(5, Config.defaultFleet().length);
    }

    @Test
    public void defaultFleetShouldReturnNewFleet() {
        assertNotEquals(Config.defaultFleet(), Config.defaultFleet());
    }

    @Test
    public void rngShouldBePure() {
        int n = 10;
        for (int i = 0; i < n; i++) {
            Config.rng().nextInt();
        }
        int first = Config.rng().nextInt();
        for (int i = 0; i < n; i++) {
            Config.rng().nextInt();
        }
        int second = Config.rng().nextInt();
        assertEquals(first, second);
    }
}
