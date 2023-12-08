package cs3343.battleship.test.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cs3343.battleship.game.Config;

public class ConfigTests {
    @Test
    public void boardSizeShouldBe10() {
        assertEquals(10, Config.BOARD_SIZE);
    }

    @Test
    public void defaultPortShouldBe1234() {
        assertEquals(1234, Config.DEFAULT_PORT);
    }

    @Test
    public void defaultFleetShouldHaveLength5() {
        assertEquals(5, Config.defaultFleet().length);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 23, 456 })
    public void rngShouldHaveCorrectSeed(int n) {
        Random expected = new Random(Config.RANDOM_SEED);
        Random configRng = Config.rng();
        for (int i = 0; i < n; i++) {
            expected.nextInt();
            configRng.nextInt();
        }
        assertEquals(expected.nextInt(), configRng.nextInt());
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
