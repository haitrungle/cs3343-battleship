package cs3343.battleship.game;

import cs3343.battleship.backend.Backend;

public final class Game {
    private static Game _game = new Game();

    public static Game create() {
        return _game;
    }

    private String name;
    private Backend backend;

    private Game() {}

    public void run() {
        Console.println(banner);

        name = Console.askName();
        Console.typeln("Welcome, " + name + "!");

        while (true) {
            int option = Console.askGameOption();
            if (option == 1) {
                Tutorial tutorial = new Tutorial();
                tutorial.run();
            } else {
                Match match = new Match(backend);
                match.run();
            }
        }
    }

    private static String banner = " ___    __   _____ _____  _     ____  __   _     _   ___  \n" +
            "| |_)  / /\\   | |   | |  | |   | |_  ( (` | |_| | | | |_) \n" +
            "|_|_) /_/--\\  |_|   |_|  |_|__ |_|__ _)_) |_| | |_| |_|   \n";
}
