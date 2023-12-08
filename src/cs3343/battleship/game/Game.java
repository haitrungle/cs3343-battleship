package cs3343.battleship.game;

import cs3343.battleship.backend.Backend;

/**
 * This class is charged with starting, managing the match and tutorial loop,
 * and exiting the game. It uses the singleton pattern.
 */
public final class Game {
    private Game() {
    }

    private static Game _game = new Game();
    static {
        Config.CONSOLE_COLOR = true;
    }

    /**
     * Returns the singleton instance of the game.
     */
    public static Game create() {
        return _game;
    }

    private String name;
    private Backend backend;
    private Console console = Console.system();

    /**
     * Starts and runs the game. This method returns when the game is exited.
     */
    public void run() {
        console.println(banner);

        name = console.askName();
        console.typeln("Welcome, " + name + "!");

        while (true) {
            int option = 0;
            try {
                option = console.askGameOption();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (option == 1) {
                Tutorial tutorial = new Tutorial(console);
                tutorial.run();
            } else {
                try {
                    Match match = new Match(backend, console);
                    match.run();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static String banner = " ___    __   _____ _____  _     ____  __   _     _   ___  \n" +
            "| |_)  / /\\   | |   | |  | |   | |_  ( (` | |_| | | | |_) \n" +
            "|_|_) /_/--\\  |_|   |_|  |_|__ |_|__ _)_) |_| | |_| |_|   \n";
}
