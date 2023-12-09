package cs3343.battleship;

import cs3343.battleship.game.Game;

public class Main {
    private static Game game = Game.getDefault();

    public static void main(String[] args) {
        game.run();
    }
}
