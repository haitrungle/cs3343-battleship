package cs3343.battleship.game;

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

    public static String textColor(String str, String color) {
        return color + str + RESET;
    }
}
