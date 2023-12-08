package cs3343.battleship.game;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Message;
import cs3343.battleship.logic.Player;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.*;

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
     * Creates a new match with the given backend and console. If backend is null,
     * ask the user and construct a backend itself.
     * 
     * @param b the backend to use, or null to ask the user and create a backend
     * @param c the console to use
     */
    public Match(Backend b, Console c) throws Exception {
        console = c;
        if (b == null) {
            b = console.askBackend();
            backend = b;
        }
        player = new Player();
        won = false;
    }

    /**
     * Runs the match. All the match logic and communication with the other party is
     * contained within this method.
     */
    public void run() throws Exception {
        Message init = Message.InitMsg();
        backend.sendMessage(init);
        Message remoteInit = backend.waitForMessage();
        assert remoteInit.getType() == Message.Type.INIT;
        myTurn = init.getTimestamp().compareTo(remoteInit.getTimestamp()) < 0;

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
        } catch (Exception e) {
            console.println(e);
        }
    }
}
