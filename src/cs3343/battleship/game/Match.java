package cs3343.battleship.game;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Message;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.*;

public class Match {
    private Player player;
    private Backend backend;
    private boolean myTurn;
    private boolean won;

    public Match(Backend backend) {
        if (backend == null) {
            backend = Console.askBackend();
            this.backend = backend;
        }
        player = new Player();
        won = false;
    }

    public void run() {
        Message init = Message.InitMsg();
        backend.sendMessage(init);
        Message remoteInit = backend.waitForMessage();
        assert remoteInit.getType() == Message.Type.INIT;
        myTurn = init.getTimestamp().compareTo(remoteInit.getTimestamp()) < 0;

        try {
            Console.println(".-------------.\n" +
                            "|  NEW MATCH  |\n" +
                            "'-------------'\n");

            Console.typeln("Setting ships. You will have 5 ships in total.");
            Console.typeln("For each ship, enter direction and start position, e.g. 'd 2,3'");
            Ship[] fleet = Config.defaultFleet();
            for (int i = 0; i < 5; i++) {
                player.printBoard();
                Console.askAndAddShip(fleet[i], player);
            }

            while (player.hasAliveShip()) {
                if (myTurn) {
                    Position pos = Console.askShot(player);

                    Message shotMsg = Message.ShotMsg(pos);
                    backend.sendMessage(shotMsg);

                    Message resultMsg = backend.waitForMessage();
                    boolean hit = resultMsg.getHit();

                    player.shotEnemy(pos, hit);

                    player.printTwoBoards();
                    Console.typeln(hit ? "You have hit an enemy ship!" : "Sorry, you miss this one.");
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

                    player.printTwoBoards();
                    Console.typeln("Enemy shot you at position " + shot);
                }
                myTurn = !myTurn;
            }
            if (won) {
                Console.typeln("\nCongratulations! You won the match!");
            } else {
                backend.sendMessage(Message.LostMsg());
                Console.typeln("\nSorry, you lost. Better luck next time.");
            }
        } catch (Exception e) {
            Console.println(e);
        }
    }
}
