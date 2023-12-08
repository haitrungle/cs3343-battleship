package cs3343.battleship.game;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3343.battleship.backend.*;
import cs3343.battleship.exceptions.*;
import cs3343.battleship.logic.*;
import cs3343.battleship.logic.ship.Direction;
import cs3343.battleship.logic.ship.Ship;

/**
 * This class represents the main game interface that handles all interactions
 * with the user, such as prompting for input and printing output. However, it
 * does not contain any game logic.
 * 
 * By default, the input and output streams of Console are set to System.in and
 * System.out. You can use the withIn() and withOut() methods to change them,
 * which is especially useful for testing.
 */
public final class Console {
    /**
     * This enum represents the colors that can be used in the console. Internally,
     * it contains the ANSI escape codes for the colors.
     */
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

    private Console() {
        in = new Scanner(System.in).useDelimiter("[,\\s]+");
        out = System.out;
    }

    private static final Console defaultInstance = new Console();

    /**
     * Returns the default instance of the Console class.
     */
    public static Console system() {
        return defaultInstance;
    }

    /**
     * Returns a new instance of the Console class, with the input and output
     * streams set to System.in and System.out. The difference between this
     * method and {@link #system()} is that the former returns a new instance,
     * while the latter always returns the same instance. Hence, you should only
     * use this if you want to customize the input or output streams, otherwise
     * retrieve the singleton instance with {@link #system()}.
     * 
     * @return a new Console instance, with the input and output streams set to
     *         `System.in` and `System.out`, respectively.
     */
    public static Console make() {
        return new Console();
    }

    /**
     * Returns this instance with the input stream set to the given string. This is
     * useful for testing.
     * 
     * @param s the string to set the input stream to
     * @return this instance with the input stream set to the given string
     */
    public Console withIn(String s) {
        in = new Scanner(s).useDelimiter("[,\\s]+");
        return this;
    }

    /**
     * Returns this instance with the output stream set to the
     * ByteArrayOutputStream. This is useful for testing.
     * 
     * @param out the ByteArrayOutputStream to set the output stream to
     * @return this instance with the output stream set to the given
     *         ByteArrayOutputStream
     */
    public Console withOut(ByteArrayOutputStream out) {
        this.out = new PrintStream(out);
        return this;
    }

    /**
     * Returns the string representation of the object with the text in the
     * specified color. This respects the CONSOLE_COLOR configuration option:
     * if it is set to false, then the method simply return `obj.toString()`.
     * 
     * @param obj   the object to print
     * @param color the color to print the object in
     * @return the string representation of the object with the text in the
     *         specified color
     */
    public static String colorize(Object obj, Color color) {
        if (!Config.CONSOLE_COLOR)
            return obj.toString();
        return color.getValue() + obj.toString() + Color.RESET.getValue();
    }

    /**
     * Prints the given string to the output stream with a typewriter effect. This
     * method respects the TYPEWRITER_EFFECT configuration option: if it is set to
     * false, then the method simply calls `out.println(str)`.
     * 
     * @param str the string to print
     */
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

    /**
     * Prints the given object to the output stream with a final newline.
     * 
     * @param obj the object to print
     */
    public void println(Object obj) {
        out.println(obj);
    }

    /**
     * Returns the name given by the user.
     * 
     * @return the given name
     */
    public String askName() {
        println("What is your name?");
        prompt();
        return in.nextLine();
    }

    /**
     * Asks the user if they want to be the Server or the Client and returns the
     * corresponding Backend. This method returns a Server with the default port
     * for the former and a Client with the given address for the latter.
     * 
     * @return either a Server or a Client as specified by the user
     */
    public Backend askBackend() throws Exception {
        boolean isServer = askIsServer();
        if (isServer) {
            return new Server(Config.DEFAULT_PORT);
        } else {
            String[] parts = askAddress();
            return new Client(parts[0], Integer.parseInt(parts[1]));
        }
    }

    /**
     * Asks the user if they want to be the Server or the Client and returns the
     * binary option. Used by {@link #askBackend()}
     * 
     * @return true if the user wants to be the Server, false otherwise
     */
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

    /**
     * Asks the user for the address of the server and returns the specified
     * address.
     * 
     * @return a String array of length 2, where the first element is the host and
     *         the second element is the port
     */
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

    /**
     * Asks the user whether to start the tutorial or a new match and return the
     * specified option.
     * 
     * @return the specified option (1 for tutorial, 2 for new match)
     */
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

    /**
     * Asks the user where they want to place a ship and add it to the given Player.
     * This method is somewhat peculiar in its mutating the passed in Ship instead
     * of constructing a new one, but this allows us to encapsulate the name and
     * length of the ship (which this method needs) without defining a new class.
     * It also required an instance of Player to validate the ship positions before
     * adding, allowing for reprompting.
     * 
     * @param ship   the ship to add, whose start position and direction will be set
     * @param player the player to add the ship to
     * @return the ship that was added, with start position and direction set
     */
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

    /**
     * Asks the user for a position and return the specified Shot. If the user enter
     * nothing, return a random shot. Unlike {@link #askAndAddShip(Ship, Player)},
     * this method does not mutate the given Player.
     * 
     * @param player the player used for validate the shot position
     * @return a Position representing the shot
     */
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

    /**
     * Returns a position parsed from the input stream.
     * 
     * @return the given Position
     * @throws InvalidInputException if the input cannot be parsed as a Position
     */
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

    /**
     * Returns a pair of direction and position parsed from the input stream.
     * 
     * @return the given Direction and Position
     * @throws InvalidInputException if the input cannot be parsed as a pair of
     *                               Direction and Position
     */
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

    /**
     * Returns a yes/no boolean option parsed from the input stream.
     * 
     * @return the given option
     * @throws InvalidInputException if the input cannot be parsed as a boolean
     */
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