package cs3343.battleship.game;

import cs3343.battleship.exceptions.PositionOutOfBoundsException;
import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.logic.*;
import cs3343.battleship.logic.ship.Ship;

public class Tutorial {
    private Player player;
    private Player enemy;

    public Tutorial() {
        player = new Player();
        enemy = new Player();
    }

    public void run() {
        startTutorial();
        addShipsToBoard();
        explainGameRules();
        startGame();
        endTutorial();
    }

    private void startTutorial() {
        Console.println(".------------.\n" +
                        "|  TUTORIAL  |\n" +
                        "'------------'\n");
        Console.typeln("In this tutorial, you will learn how to play Battleship.\n");
    }

    private void addShipsToBoard() {
        Console.typeln("Let's start by placing your ships on the board.");
        Console.typeln("The board is a 10x10 grid. You will place your ships on this grid.");

        player.printBoard();

        Console.typeln("You will have 5 ships of different lengths:");
        Console.typeln("1. Aircraft Carrier of length 5");
        Console.typeln("2. Battleship of length 4");
        Console.typeln("3. Cruiser of length 3");
        Console.typeln("4. Submarine of length 3");
        Console.typeln("5. Destroyer of length 2\n");
        
        Console.typeln("Place a ship by specifying the direction and the starting position:");
        Console.typeln("- Direction can either be 'd'/'down' or 'r'/'right'");
        Console.typeln("- Starting position can be specified by '[row],[col]'");
        Console.typeln("For example, enter 'd 2,3' to place a ship in the down direction starting at position 2,3.\n");

        Console.typeln("Start by placing your Aircraft Carrier.");
        Ship[] fleet = Config.defaultFleet();
        Console.askAndAddShip(fleet[0], player);
        player.printBoard();
        Console.typeln("Great job! You have placed your first ship.");
        Console.typeln("Next, place the Battleship, which has length 4.");
        Console.askAndAddShip(fleet[1], player);
        player.printBoard();
        Console.typeln("Fantastic! You can continue to place the rest of your ships.");
        Console.typeln("Tip: Press enter without any input to place your ship randomly.");

        for (int i = 2; i < fleet.length; i++) {
            Console.askAndAddShip(fleet[i], player);
            player.printBoard();
        }

        Ship[] enemyFleet = Config.defaultFleet();
        for (Ship s : enemyFleet) {
            enemy.addShipRandom(s);
        }

        Console.typeln("All ships have been placed on your board.");
        Console.typeln("Now, let's move on to the game rules.\n");
    }

    private void explainGameRules() {
        Console.typeln("The objective of the game is to sink all of your opponent's ships.");
        Console.typeln("You and your opponent will take turns firing shots at each other's boards.");
        Console.typeln("If your shot hits an enemy ship, it's a HIT. Otherwise, it's a MISS.\n");
    }

    private void startGame() {
        Console.typeln("Start by entering where you want to shoot at.");
        Console.typeln("For example, you can enter '3,4' to fire a shot at position 3,4.");
        turn();
        while (true) {
            if (!player.hasAliveShip()) {
                Console.typeln("GAME OVER! You have lost the game.");
                break;
            }
            if (!enemy.hasAliveShip()) {
                Console.typeln("GAME OVER! You have won the game.");
                break;
            }
            turn();
        }
    }

    private void endTutorial() {
        Console.typeln("Congratulations! You have completed the tutorial.");
        Console.typeln("You can now play the game by choosing option [2].");
    }

    private void turn() {
        try {
            Position shot = Console.askShot(player);
            boolean hit = enemy.getShot(shot);
            player.shotEnemy(shot, hit);
            player.printTwoBoards();
            Console.typeln("You have " + (hit ? "hit" : "missed") + " an enemy ship!");
        } catch (Exception e) {
            Console.println("UNREACHABLE");
        }

        try {
            Position shot = enemy.getRandomShot();
            boolean hit = player.getShot(shot);
            enemy.shotEnemy(shot, hit);
            player.printTwoBoards();
            Console.typeln("The enemy shot at " + shot + ". They have " + (hit ? "hit" : "missed") + " your ships!");
        } catch (Exception e) {
            Console.println("UNREACHABLE");
        }
    }
}