package cs3343.battleship.game;

import cs3343.battleship.backend.Backend;
import cs3343.battleship.backend.Message;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.*;

public class Match {
    private Player player;
    private Backend backend;
    private boolean myTurn;

    public Match(Backend backend) {
        if (backend == null) {
            backend = Console.askBackend();
            this.backend = backend;
        }
        player = new Player();
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
                    System.out.println(hit ? "You have hit an enemy ship!" : "Sorry, you miss this one.");
                } else {
                    Message shotMsg = backend.waitForMessage();

                    Position shot = shotMsg.getShot();
                    boolean result = player.getShot(shot);

                    Message resultMsg = Message.ResultMsg(result);
                    backend.sendMessage(resultMsg);

                    player.printTwoBoards();
                    System.out.println("Enemy shot at position " + shot);
                }
                myTurn = !myTurn;
            }
        } catch (Exception e) {
            Console.println(e);
        }
    }
}
