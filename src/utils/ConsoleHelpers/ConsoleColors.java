package utils.ConsoleHelpers;

/**
 * The ConsoleColors class provides methods to apply ANSI colors to strings.
 */
public class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";

    // Regular Colors
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String MAGENTA = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String WHITE = "\033[37m";

    /**
     * Returns the given text in the specified color.
     *
     * @param color the ANSI color code
     * @param text  the text to color
     * @return the colored text
     */
    public static String colorize(String color, String text) {
        return color + text + RESET;
    }

    public static String colorizeAndBold(String text, String color) {
        String colorCode;
        if (color.equals("red"))
            colorCode = RED;
        else if (color.equals("green"))
            colorCode = GREEN;
        else if (color.equals("yellow"))
            colorCode = YELLOW;
        else if (color.equals("blue"))
            colorCode = BLUE;
        else if (color.equals("magenta"))
            colorCode = MAGENTA;
        else if (color.equals("cyan"))
            colorCode = CYAN;
        else if (color.equals("white"))
            colorCode = WHITE;
        else
            throw new IllegalArgumentException("Invalid color");
        return "\033[1m" + colorCode + text + RESET;
        
    }

    /**
     * Returns the given text in red color.
     *
     * @param text the text to color
     * @return the colored text
     */
    public static String red(String text) {
        return colorize(RED, text);
    }

    /**
     * Returns the given text in green color.
     *
     * @param text the text to color
     * @return the colored text
     */
    public static String green(String text) {
        return colorize(GREEN, text);
    }

    /**
     * Returns the given text in yellow color.
     *
     * @param text the text to color
     * @return the colored text
     */
    public static String yellow(String text) {
        return colorize(YELLOW, text);
    }

    /**
     * Returns the given text in blue color.
     *
     * @param text the text to color
     * @return the colored text
     */
    public static String blue(String text) {
        return colorize(BLUE, text);
    }

    /**
     * Returns the given text in magenta color.
     *
     * @param text the text to color
     * @return the colored text
     */
    public static String magenta(String text) {
        return colorize(MAGENTA, text);
    }

    /**
     * Returns the given text in cyan color.
     *
     * @param text the text to color
     * @return the colored text
     */
    public static String cyan(String text) {
        return colorize(CYAN, text);
    }

    /**
     * Returns the given text in white color.
     *
     * @param text the text to color
     * @return the colored text
     */
    public static String white(String text) {
        return colorize(WHITE, text);
    }
}
