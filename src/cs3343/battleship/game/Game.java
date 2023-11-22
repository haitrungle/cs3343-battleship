package cs3343.battleship.game;

import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3343.battleship.backend.*;
import cs3343.battleship.exceptions.*;
import cs3343.battleship.logic.*;
import cs3343.battleship.logic.ship.*;

public final class Game {
    private static Game _game = new Game();

    public static Game create() {
        if (_game == null)
            _game = new Game();
        return _game;
    }

    private Scanner sc;
    private Player player;
    private boolean myTurn;
    private Backend backend;
    private Ship[] defaultShips = { new AircraftCarrier(), new Battleship(), new Cruiser(), new Submarine(),
            new Destroyer() };

    private Game() {
        sc = new Scanner(System.in);
        sc.useDelimiter("[,\\s]+");
    }

    public void run() {
        System.out.println(banner);

        player = askPlayerName();

        backend = askBackend();

        Message init = Message.InitMsg();
        backend.sendMessage(init);
        Message remoteInit = backend.waitForMessage();
        assert remoteInit.type == Message.Type.INIT;
        myTurn = init.timestamp.compareTo(remoteInit.timestamp) < 0;

        try {
            while (true) { // main game loop
                System.out.println("\nNEW GAME\n");

                System.out.println("Setting ships. You will have 5 ships in total.");
                System.out.println("For each ship, enter direction and start position, e.g. 'd 2,3'");
                for (int i = 0; i < 5; i++) {
                    player.printBoard();
                    Ship ship = askShip(i);
                    player.addShip(ship);
                }

                while (player.hasAliveShip()) {
                    if (myTurn) {
                        Position pos = askShot();

                        Message shotMsg = Message.ShotMsg(pos);
                        backend.sendMessage(shotMsg);

                        Message resultMsg = backend.waitForMessage();
                        assert resultMsg.type == Message.Type.RESULT;

                        player.shotEnemy(pos, resultMsg.hit);

                        player.printTwoBoards();
                        System.out.println(resultMsg.hit ? "You have hit an enemy ship!" : "Sorry, you miss this one.");
                    } else {
                        Message shotMsg = backend.waitForMessage();
                        assert shotMsg.type == Message.Type.SHOT;

                        Position shot = shotMsg.shot;
                        boolean result = player.getShot(shot);

                        Message resultMsg = Message.ResultMsg(result);
                        backend.sendMessage(resultMsg);

                        player.printTwoBoards();
                        System.out.println("Enemy shot at position " + shot);
                    }
                    myTurn = !myTurn;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Player askPlayerName() {
        System.out.println("What is your name?");
        prompt();
        return new Player(sc.nextLine());
    }

    private Backend askBackend() {
        boolean isServer = askIsServer();
        if (isServer) {
            return new Server();
        } else {
            String[] parts = askAddress();
            return new Client(parts[0], Integer.parseInt(parts[1]));
        }
    }

    private boolean askIsServer() {
        System.out.println("Are you the server? [y/n]");
        while (true) {
            prompt();
            try {
                boolean option = readBoolean();
                return option;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String[] askAddress() {
        System.out.println(
                "Enter the address of server, e.g. '127.0.0.1:1234'");
        String s;
        while (true) {
            prompt();
            s = sc.nextLine();
            if (s.equals(""))
                return new String[] { "localhost", "1234" };
            if (s.contains(":"))
                return s.split(":");
            System.out.println("Invalid address. Please enter both host and port.");
        }
    }

    private Ship askShip(int i) {
        Ship ship = defaultShips[i];
        System.out.println(String.format("Ship %d: %s", i + 1, ship.introduce()));
        System.out.println("Enter your ship direction and start position.");
        while (true) {
            prompt();
            try {
                Pair<Direction, Position> pair = readPositionAndDirection();
                ship.setDirection(pair.first);
                ship.setStartPosition(pair.second);
                Position pos;
                if ((pos = player.hasOverlapShip(ship)) != null)
                    throw new OverlapShipException(pos);
                return ship;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Position askShot() {
        System.out.println("Where do you want to shoot?");
        while (true) {
            prompt();
            try {
                Position shot = readPosition();
                if (player.hasShotEnemyAt(shot))
                    throw new PositionShotTwiceException(shot);
                return shot;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Position readPosition() throws InvalidInputException {
        String errorMsg = "Cannot parse Position.\nPlease enter in the format '[row],[col]', like '2,3'.";
        try {
            String line = sc.nextLine().trim();
            String[] parts = line.split("[,\\s]+");
            if (parts.length != 2)
                throw new InvalidInputException(errorMsg);
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            return new Position(row, col);
        } catch (NoSuchElementException e) {
            throw new InvalidInputException(errorMsg);
        }
    }

    private Pair<Direction, Position> readPositionAndDirection() throws InvalidInputException {
        String errorMsg = "Cannot parse Direction and Position.\nPlease enter in the format '[d/r] [row],[col]', like 'd 2,3'.";
        try {
            String line = sc.nextLine().trim();
            String[] parts = line.split("[,\\s]+");
            if (parts.length != 3)
                throw new InvalidInputException(errorMsg);
            Direction dir = Direction.decode(parts[0]);
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);
            return new Pair<Direction, Position>(dir, new Position(row, col));
        } catch (NoSuchElementException e) {
            throw new InvalidInputException(errorMsg);
        }
    }

    private boolean readBoolean() throws InvalidInputException {
        try {
            String s = sc.nextLine().toLowerCase();
            if (s.equals("y") || s.equals("yes"))
                return true;
            else if (s.equals("n") || s.equals("no"))
                return false;
            else
                throw new InvalidInputException("Can only be yes or no. Please enter 'y'/'yes' or 'n'/'no'.");
        } catch (NoSuchElementException e) {
            throw new InvalidInputException("Cannot read line. Please enter 'y'/'yes' or 'n'/'no'.");
        }
    }

    private void prompt() {
        System.out.print("> ");
    }

    private static String banner = " ___    __   _____ _____  _     ____  __   _     _   ___  \n" +
            "| |_)  / /\\   | |   | |  | |   | |_  ( (` | |_| | | | |_) \n" +
            "|_|_) /_/--\\  |_|   |_|  |_|__ |_|__ _)_) |_| | |_| |_|   \n";
}

final class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A a, B b) {
        first = a;
        second = b;
    }
}