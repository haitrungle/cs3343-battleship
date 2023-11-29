package cs3343.battleship.game;

import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3343.battleship.backend.*;
import cs3343.battleship.exceptions.*;
import cs3343.battleship.logic.*;
import cs3343.battleship.logic.ship.Ship;

public final class Console {
    public static String RESET = "\u001B[0m";
    public static String BLACK = "\u001B[30m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";
    public static String YELLOW = "\u001B[33m";
    public static String BLUE = "\u001B[34m";
    public static String PURPLE = "\u001B[35m";
    public static String CYAN = "\u001B[36m";
    public static String WHITE = "\u001B[37m";

    public static String BLACK_BG = "\u001B[40m";
    public static String RED_BG = "\u001B[41m";
    public static String GREEN_BG = "\u001B[42m";
    public static String YELLOW_BG = "\u001B[43m";
    public static String BLUE_BG = "\u001B[44m";
    public static String PURPLE_BG = "\u001B[45m";
    public static String CYAN_BG = "\u001B[46m";
    public static String WHITE_BG = "\u001B[47m";

    private static Scanner sc = new Scanner(System.in).useDelimiter("[,\\s]+");

    public void setScanner(Scanner s) {
        sc = s;
    }

    public static String textColor(String str, String color) {
        return color + str + RESET;
    }

    // Print text with a typewriter effect
    public static void typeln(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i));
            try {
                Thread.sleep(75);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    static String askName() {
        println("What is your name?");
        prompt();
        return sc.nextLine();
    }

    static Backend askBackend() {
        boolean isServer = askIsServer();
        if (isServer) {
            return new Server(Config.DEFAULT_PORT);
        } else {
            String[] parts = askAddress();
            return new Client(parts[0], Integer.parseInt(parts[1]));
        }
    }

    private static boolean askIsServer() {
        println("Are you the server? [y/n]");
        while (true) {
            prompt();
            try {
                boolean option = readBoolean();
                return option;
            } catch (InvalidInputException e) {
                println(e.getMessage());
            }
        }
    }

    private static String[] askAddress() {
        println("Enter the address of server, e.g. '127.0.0.1:1234'");
        String s;
        while (true) {
            prompt();
            s = sc.nextLine();
            if (s.equals(""))
                return new String[] { "localhost", "1234" };
            if (s.contains(":"))
                return s.split(":");
            println("Invalid address. Please enter both host and port.");
        }
    }

    static int askGameOption() {
        println("\nChoose an option:");
        println("[1] Tutorial");
        println("[2] New match");
        while (true) {
            prompt();
            try {
                int option = Integer.parseInt(sc.nextLine());
                if (option == 1 || option == 2)
                    return option;
                else
                    throw new InvalidInputException("Can only be 1 or 2. Please enter 1 or 2.");
            } catch (InvalidInputException e) {
                println(e.getMessage());
            } catch (NumberFormatException e) {
                println("Cannot parse integer. Please enter 1 or 2.");
            }
        }
    }

    static Ship askAndAddShip(Ship ship, Player player) {
        println(String.format(ship.introduce()));
        println("Enter your ship direction and start position.");
        while (true) {
            prompt();
            try {
                Pair<Direction, Position> pair = readPositionAndDirection();
                if (pair == null)
                    return player.addShipRandom(ship);
                ship.setDirection(pair.first);
                ship.setStartPosition(pair.second);
                player.addShip(ship);
                return ship;
            } catch (InvalidInputException e) {
                println(e.getMessage());
            }
        }
    }

    static Position askShot(Player player) {
        println("Where do you want to shoot?");
        while (true) {
            prompt();
            try {
                Position shot = readPosition();
                if (shot == null)
                    shot = player.getRandomShot();
                player.checkShot(shot);
                return shot;
            } catch (InvalidInputException e) {
                println(e.getMessage());
            }
        }
    }

    private static Position readPosition() throws InvalidInputException {
        String errorMsg = "Cannot parse Position.\nPlease enter in the format '[row],[col]', like '2,3'.";
        try {
            String line = sc.nextLine().trim();
            // random position
            if (line.equals(""))
                return null;
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

    private static Pair<Direction, Position> readPositionAndDirection() throws InvalidInputException {
        String errorMsg = "Cannot parse Direction and Position.\nPlease enter in the format '[d/r] [row],[col]', like 'd 2,3'.";
        try {
            String line = sc.nextLine().trim();
            // random ship
            if (line.equals(""))
                return null;
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

    private static boolean readBoolean() throws InvalidInputException {
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

    private static void prompt() {
        System.out.print("> ");
    }
}

final class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A a, B b) {
        first = a;
        second = b;
    }
}