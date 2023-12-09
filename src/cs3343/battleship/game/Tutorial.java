package cs3343.battleship.game;

import cs3343.battleship.logic.*;
import cs3343.battleship.logic.Ship;

/**
 * This class contains the tutorial of the game. In the tutorial, the player
 * plays with a dumb computer that places ships and shoots randomly.
 */
public final class Tutorial {
    private Player player;
    private Player enemy;
    private Console console;

    /**
     * Constructs a new tutorial with the given Console.
     * 
     * @param c the console to use for input and output
     */
    public Tutorial(Console c) {
        player = new Player();
        enemy = new Player();
        console = c;
    }

    /**
     * Runs the tutorial.
     */
    public void run() {
        startTutorial();
        addShipsToBoard();
        explainGameRules();
        startGame();
        endTutorial();
    }

    private void startTutorial() {
        console.println(".------------.\n" +
                "|  TUTORIAL  |\n" +
                "'------------'\n");
        console.typeln("In this tutorial, you will learn how to play Battleship.\n");
    }

    private void addShipsToBoard() {
        console.typeln("Let's start by placing your ships on the board.");
        console.typeln("The board is a 10x10 grid. You will place your ships on this grid.");

        console.println(player.boardToString());

        console.typeln("You will have 5 ships of different lengths:");
        console.typeln("1. Aircraft Carrier of length 5");
        console.typeln("2. Battleship of length 4");
        console.typeln("3. Cruiser of length 3");
        console.typeln("4. Submarine of length 3");
        console.typeln("5. Destroyer of length 2\n");

        console.typeln("Place a ship by specifying the direction and the starting position:");
        console.typeln("- Direction can either be 'd'/'down' or 'r'/'right'");
        console.typeln("- Starting position can be specified by '[row],[col]'");
        console.typeln("For example, enter 'd 2,3' to place a ship in the down direction starting at position 2,3.\n");

        console.typeln("Start by placing your Aircraft Carrier.");
        Ship[] fleet = Config.defaultFleet();
        console.askAndAddShip(fleet[0], player);
        console.println(player.boardToString());
        console.typeln("Great job! You have placed your first ship.");
        console.typeln("Next, place the Battleship, which has length 4.");
        console.askAndAddShip(fleet[1], player);
        console.println(player.boardToString());
        console.typeln("Fantastic! You can continue to place the rest of your ships.");
        console.typeln("Tip: Press enter without any input to place your ship randomly.");

        for (int i = 2; i < fleet.length; i++) {
            console.askAndAddShip(fleet[i], player);
            console.println(player.boardToString());
        }

        Ship[] enemyFleet = Config.defaultFleet();
        for (Ship s : enemyFleet) {
            enemy.addShipRandom(s);
        }

        console.typeln("All ships have been placed on your board.");
        console.typeln("Now, let's move on to the game rules.\n");
    }

    private void explainGameRules() {
        console.typeln("The objective of the game is to sink all of your opponent's ships.");
        console.typeln("You and your opponent will take turns firing shots at each other's boards.");
        console.typeln("If your shot hits an enemy ship, it's a HIT. Otherwise, it's a MISS.\n");
    }

    private void startGame() {
        console.typeln("Start by entering where you want to shoot at.");
        console.typeln("For example, you can enter '3,4' to fire a shot at position 3,4.");
        turn();
        while (true) {
            if (!player.hasAliveShip()) {
                console.typeln("GAME OVER! You have lost the game.");
                break;
            }
            if (!enemy.hasAliveShip()) {
                console.typeln("GAME OVER! You have won the game.");
                break;
            }
            turn();
        }
    }

    private void endTutorial() {
        console.typeln("Congratulations! You have completed the tutorial.");
        console.typeln("You can now play the game by choosing option [2].");
    }

    /**
     * Runs a turn of the game. Since there is no Backend involved, this technically
     * plays two turns together: the player's turn and the computer's turn.
     */
    private void turn() {
        // Player's turn
        try {
            Position shot = console.askShot(player);
            boolean hit = enemy.getShot(shot);
            player.shotEnemy(shot, hit);
            console.println(player.twoBoardsToString());
            console.typeln("You have " + (hit ? "hit" : "missed") + " an enemy ship!");
        } catch (Exception e) {
            console.println("UNREACHABLE");
        }

        // Computer's turn
        try {
            Position shot = enemy.getRandomShot();
            boolean hit = player.getShot(shot);
            enemy.shotEnemy(shot, hit);
            console.println(player.twoBoardsToString());
            console.typeln("The enemy shot at " + shot + ". They have " + (hit ? "hit" : "missed") + " your ships!");
        } catch (Exception e) {
            console.println("UNREACHABLE");
        }
    }
}