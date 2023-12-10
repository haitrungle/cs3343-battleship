package cs3343.battleship.game;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Message;
import cs3343.battleship.exceptions.GameException;
import cs3343.battleship.exceptions.NullObjectException;
import cs3343.battleship.exceptions.PositionOutOfBoundsException;
import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.logic.Player;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.Ship;

/**
 * This class represents a match that a player is in with another player. Since
 * two players can be on different computers, perhaps somewhat unintuitively,
 * this class does not contain two players.
 */
public final class Match {
    private Player player;
    private Backend backend;
    private Console console;
    // Whether it is this player's turn to shoot.
    private boolean myTurn;
    // Whether this player has won the match.
    private boolean won;

    /**
     * Creates a new match with the given backend and console.
     * 
     * @param b the backend to use
     * @param c the console to use
     */
    public Match(Backend b, Console c) {
        console = c;
        backend = b;
        player = new Player(Config.getBoardSize());
        won = false;
    }

    /**
     * Returns the backend of this Match.
     * 
     * @return the backend of this Match
     */
    public Backend getBackend() {
        return backend;
    }

    /**
     * Runs the match. All the match logic and communication with the other party is
     * contained within this method.
     * 
     * @return whether this player has won the match
     * @throws InterruptedException if the thread is interrupted while waiting for
     *                              the backend to be ready
     * @throws GameException        if an unexpected error occurs
     */
    public boolean run() throws InterruptedException, GameException {
        waitUntilReady();
        Message init = Message.InitMsg();
        backend.sendMessage(init);
        Message remoteInit = backend.waitForMessage();
        myTurn = init.getTimestamp().compareTo(remoteInit.getTimestamp()) < 0;

        console.println(".-------------.\n" +
                "|  NEW MATCH  |\n" +
                "'-------------'\n");

        console.typeln("Setting ships. You will have 5 ships in total.");
        console.typeln("For each ship, enter direction and start position, e.g. 'd 2,3'");
        Ship[] fleet = Config.defaultFleet();
        for (int i = 0; i < 5; i++) {
            console.println(player.boardToString());
            console.askAndAddShip(fleet[i], player);
        }
        console.println(player.boardToString());

        while (player.hasAliveShip()) {
            try {
                if (myTurn) {
                    Position pos = console.askShot(player);

                    Message shotMsg = Message.ShotMsg(pos);
                    backend.sendMessage(shotMsg);

                    Message resultMsg = backend.waitForMessage();
                    boolean hit = resultMsg.getHit();

                    player.shotEnemy(pos, hit);

                    console.println(player.twoBoardsToString());
                    console.typeln(hit ? "You have hit an enemy ship!" : "Sorry, you miss this one.");
                } else {
                    console.typeln("Waiting for the other player to make a move...");
                    Message msg = backend.waitForMessage();
                    if (msg.getType() == Message.Type.LOST) {
                        won = true;
                        break;
                    }

                    Position shot = msg.getShot();
                    boolean result = player.getShot(shot);

                    Message resultMsg = Message.ResultMsg(result);
                    backend.sendMessage(resultMsg);

                    console.println(player.twoBoardsToString());
                    console.typeln("Enemy shot you at position " + shot);
                }
                myTurn = !myTurn;
            } catch (PositionOutOfBoundsException | PositionShotTwiceException | NullObjectException e) {
                throw new GameException("Unknown error (this should not happened): " + e.getMessage());
            }
        }
        if (won) {
            console.typeln("\nCongratulations! You won the match!");
        } else {
            backend.sendMessage(Message.LostMsg());
            console.typeln("\nSorry, you lost. Better luck next time.");
        }
        return won;
    }

    private void waitUntilReady() throws InterruptedException {
        if (!backend.isReady())
            console.typeln("Waiting for another player to connect...");
        while (!backend.isReady())
            Thread.sleep(1000);
    }
}
