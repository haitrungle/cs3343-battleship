package cs3343.battleship.game;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Message;
import cs3343.battleship.logic.Player;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.*;

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
    public Match(Backend b, Console c) throws Exception {
        console = c;
        backend = b;
        player = new Player();
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
     */
    public boolean run() {
        try {
            waitUntilReady();
            Message init = Message.InitMsg();
            backend.sendMessage(init);
            Message remoteInit = backend.waitForMessage();
            assert remoteInit.getType() == Message.Type.INIT;
            myTurn = init.getTimestamp().compareTo(remoteInit.getTimestamp()) < 0;
        } catch (Exception e) {
            console.println(e);
        }

        try {
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
            }
            if (won) {
                console.typeln("\nCongratulations! You won the match!");
            } else {
                backend.sendMessage(Message.LostMsg());
                console.typeln("\nSorry, you lost. Better luck next time.");
            }
            return won;
        } catch (Exception e) {
            console.println(e);
            return false;
        }
    }

    private void waitUntilReady() throws Exception {
        if (!backend.isReady()) console.typeln("Waiting for another player to connect...");
        while (!backend.isReady())
            Thread.sleep(1000);
    }
}
