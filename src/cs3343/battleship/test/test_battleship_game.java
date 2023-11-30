package cs3343.battleship.test;


import java.io.*;

import cs3343.battleship.game.*;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;

import cs3343.battleship.exceptions.*;
import org.junit.Test;
import static org.testng.AssertJUnit.*;
public class test_battleship_game {

    @Test
    public void test_config_1(){
        Config c = new Config();
        assertEquals(10, Config.BOARD_SIZE);
    }
    @Test
    public void test_config_2(){

        assertEquals(1234, Config.DEFAULT_PORT);
    }

    @Test
    public void test_config_default_fleet(){

        assertEquals(5, Config.defaultFleet().length);
    }
//	@Test
//	public void test_Player_1() {
//		Player p = new Player("KAI");
//		assertEquals("KAI", p.getName());
//	}
//
//	@Test
//	public void test_Player_2() throws Exception{
//		Player p = new Player("KAI");
//		p.addShip(null);
//		Exception exc = assertThrows(AddshipException.class, () -> p.addShip(null));
//
//		assertEquals("Ship is not found", exc.getMessage());
//	}
}
