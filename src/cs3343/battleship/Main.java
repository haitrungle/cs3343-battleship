package cs3343.battleship;

import cs3343.battleship.game.*;


public class Main {
	private static Game game = Game.create();
	public static void main(String[] args) {
		game.run();
	}
}
