package cs3343.battleship.game;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3343.battleship.backend.*;
import cs3343.battleship.exceptions.*;
import cs3343.battleship.logic.*;
import cs3343.battleship.logic.ship.Ship;

public final class Console {
    public static enum Color {
        RESET("\u001B[0m"),
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");
    
        private String value;
    
        Color(String value) {
            this.value = value;
        }
    
        public String getValue() {
            return value;
        }
    }

    private Scanner in;
    private PrintStream out;

    private Console() {}

    private static final Console defaultInstance = new Console();

    static {
        defaultInstance.in = new Scanner(System.in).useDelimiter("[,\\s]+");
        defaultInstance.out = System.out;
    }

    public static Console system() {
        return defaultInstance;
    }

    public static Console make() {
        return new Console();
    }

    public Console withIn(String s) {
        in = new Scanner(s).useDelimiter("[,\\s]+");
        return this;
    }

    public Console withOut(ByteArrayOutputStream out) {
        this.out = new PrintStream(out);
        return this;
    }

    public static String colorize(Object obj, Color color) {
        return color.getValue() + obj.toString() + Color.RESET.getValue();
    }

    // Print text with a typewriter effect
    public void typeln(String str) {
        if (!Config.TYPEWRITER_EFFECT) {
            out.println(str);
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            out.print(str.charAt(i));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println();
    }

    public void println(Object obj) {
        out.println(obj);
    }

    public String askName() {
        println("What is your name?");
        prompt();
        return in.nextLine();
    }

    public Backend askBackend() throws Exception {
        boolean isServer = askIsServer();
        if (isServer) {
            return new Server(Config.DEFAULT_PORT);
        } else {
            String[] parts = askAddress();
            return new Client(parts[0], Integer.parseInt(parts[1]));
        }
    }

    private boolean askIsServer() {
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

    private String[] askAddress() {
        println("Enter the address of server, e.g. '127.0.0.1:1234'");
        String s;
        while (true) {
            prompt();
            s = in.nextLine();
            if (s.equals(""))
                return new String[] { "localhost", "1234" };
            if (s.contains(":"))
                return s.split(":");
            println("Invalid address. Please enter both host and port.");
        }
    }

    public int askGameOption() throws Exception {
        println("\nChoose an option:");
        println("[1] Tutorial");
        println("[2] New match");
        while (true) {
            prompt();
            try {
                int option = Integer.parseInt(in.nextLine());
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

    public Ship askAndAddShip(Ship ship, Player player) {
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
            } catch (Exception e) {
                println(e.getMessage());
            }
        }
    }

    public Position askShot(Player player) {
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

    private Position readPosition() throws InvalidInputException {
        String errorMsg = "Cannot parse Position.\nPlease enter in the format '[row],[col]', like '2,3'.";
        try {
            String line = in.nextLine().trim();
            // random position
            if (line.equals(""))
                return null;
            String[] parts = line.split("[,\\s]+");
            if (parts.length != 2)
                throw new InvalidInputException(errorMsg);
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            return new Position(row, col);
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new InvalidInputException(errorMsg);
        }
    }

    private Pair<Direction, Position> readPositionAndDirection() throws InvalidInputException {
        String errorMsg = "Cannot parse Direction and Position.\nPlease enter in the format '[d/r] [row],[col]', like 'd 2,3'.";
        try {
            String line = in.nextLine().trim();
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
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new InvalidInputException(errorMsg);
        }
    }

    private boolean readBoolean() throws InvalidInputException {
        try {
            String s = in.nextLine().toLowerCase();
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
        out.print("> ");
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