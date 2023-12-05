package cs3343.battleship.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cs3343.battleship.game.Config;

public class GameTests {
    @Test
    public void test_config_1() {
        assertEquals(10, Config.BOARD_SIZE);
    }

    @Test
    public void test_config_2() {
        assertEquals(1234, Config.DEFAULT_PORT);
    }

    @Test
    public void test_config_default_fleet() {
        assertEquals(5, Config.defaultFleet().length);
    }

    // @Test
    // public void test_Player_1() {
    // Player p = new Player("KAI");
    // assertEquals("KAI", p.getName());
    // }
    //
    // @Test
    // public void test_Player_2() throws Exception{
    // Player p = new Player("KAI");
    // p.addShip(null);
    // Exception exc = assertThrows(AddshipException.class, () -> p.addShip(null));
    //
    // assertEquals("Ship is not found", exc.getMessage());
    // }
}
