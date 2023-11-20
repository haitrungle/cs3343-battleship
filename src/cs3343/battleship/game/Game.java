package cs3343.battleship.game;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.*;

import cs3343.battleship.backend.*;
import cs3343.battleship.exceptions.PositionShotTwiceException;
import cs3343.battleship.logic.*;

public final class Game {
    private Scanner sc;
    private Player player;
    private boolean myTurn;
    private boolean isServer;
    private Backend backend;

    public Game() {
        sc = new Scanner(System.in);
        sc.useDelimiter("[,\\s]+");
    }

    public void run() {
        System.out.println(banner);

        player = askPlayerName();

        isServer = askIsServer();
        if (isServer) {
            backend = new Server(1234);
        } else {
            String[] parts = askAddress();
            backend = new Client(parts[0], Integer.parseInt(parts[1]));
        }

        Message init = Message.InitMsg();
        backend.sendMessage(init);
        Message remoteInit = backend.waitForMessage();
        assert remoteInit.type == Message.Type.INIT;
        myTurn = init.timestamp.compareTo(remoteInit.timestamp) < 0;

        try {
            while (true) { // main game loop
                System.out.println("\nNEW GAME\n");
                player.printBoard();

                int[] lengths = { 5, 4, 3, 3, 2 };
                String[] names = { "Aircraft Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer" };
                System.out.println("Setting ships. You will have 5 ships in total.");
                System.out.println("For each ship, enter direction and start position, e.g. 'd 2,3'");
                for (int i = 0; i < 5; i++) {
                    final String name = names[i];
                    final int length = lengths[i];
                    System.out.println((i + 1) + ". " + name + ": length " + length);
                    askShip((Direction d, Position p) -> {
                        Ship ship = new Ship(name, length, p, d);
                        player.addShip(ship);
                    });
                    player.printBoard();
                }

                while (player.hasAliveShip()) {
                    if (myTurn) {
                        Position pos = askShot();

                        Message shotMsg = Message.ShotMsg(pos);
                        backend.sendMessage(shotMsg);
                        Message resultMsg = backend.waitForMessage();
                        assert resultMsg.type == Message.Type.RESULT;

                        player.shotEnemy(pos, resultMsg.hit);
                        System.out.println(resultMsg.hit ? "You have hit an enemy ship!" : "Sorry, you miss this one.");
                    } else {
                        Message shotMsg = backend.waitForMessage();
                        assert shotMsg.type == Message.Type.SHOT;

                        Position shot = shotMsg.shot;
                        boolean result = player.getShot(shot);
                        System.out.println("Enemy shot at position " + shot);

                        Message resultMsg = Message.ResultMsg(result);
                        backend.sendMessage(resultMsg);
                    }
                    myTurn = !myTurn;
                    player.printTwoBoards();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public Player askPlayerName() {
        System.out.println("What is your name?");
        prompt();
        return new Player(sc.nextLine());
    }

    public boolean askIsServer() {
        System.out.println("Are you the server? [y/n]");
        return readBoolean();
    }

    public String[] askAddress() {
        System.out.println(
            "Enter the address of server, e.g. '127.0.0.1:1234'");
        String s;
        while (true) {
            prompt();
            s = sc.nextLine();
            if (s.contains(":")) return s.split(":");
            System.out.println("Invalid address. Please enter both host and port.");
        }
    }

    public void askShip(BiConsumer<Direction, Position> f) {
        System.out.println("Enter your ship direction and start position, e.g. 'd 2,3'");
        while (true) {
            prompt();
            try {
                Direction direction = Direction.decode(sc.next());
                int row = sc.nextInt();
                int col = sc.nextInt();
                Position position = new Position(row, col);
                f.accept(direction, position);
                return;
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage() + ". Please try again.");
            }
        }
    }

    public Position askShot() {
        System.out.println("Where do you want to shoot?");
        while (true) {
            prompt();
            try {
                int row = sc.nextInt();
                int col = sc.nextInt();
                Position shot = new Position(row, col);
                if (player.hasShotEnemyAt(shot))
                    throw new PositionShotTwiceException(shot);
                return shot;
            } catch (NoSuchElementException e) {
                System.out.println("Invalid input: Cannot parse Position. Please enter in the format '[row],[col]', like '2,3'.");
            } catch (PositionShotTwiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean readBoolean() {
        String s;
        while (true) {
            prompt();
            s = sc.nextLine().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Invalid option. Please enter 'y' or 'n'.");
        }
    }

    private void prompt() {
        System.out.print("> ");
    }

    private static String banner =
    " ___    __   _____ _____  _     ____  __   _     _   ___  \n" +
    "| |_)  / /\\   | |   | |  | |   | |_  ( (` | |_| | | | |_) \n" +
    "|_|_) /_/--\\  |_|   |_|  |_|__ |_|__ _)_) |_| | |_| |_|   \n";
}
