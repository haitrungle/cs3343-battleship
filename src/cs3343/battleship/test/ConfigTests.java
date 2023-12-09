package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cs3343.battleship.game.Config;

public class ConfigTests {
    @Test
    public void defaultBoardSizeShouldBe10() {
        assertEquals(10, Config.getBoardSize());
    }

    @Test
    public void defaultServerPortShouldBe1234() {
        assertEquals(1234, Config.getServerPort());
    }

    @Test
    public void defaultFleetShouldHaveLength5() {
        assertEquals(5, Config.defaultFleet().length);
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
