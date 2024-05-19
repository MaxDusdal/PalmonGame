package utils.ConsoleHelpers;

/**
 * The ConsoleColors class provides methods to apply ANSI colors to strings.
 */
public class ConsoleColors {
    // Special ANSI escape code, that resets the text color to the default color.
    public static final String RESET = "\033[0m";

    // ANSI color codes
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
    public static String colorize(String text, String color) {
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
        return colorCode + text + RESET;
    }

    /**
     * Returns the given text in the specified color and bold.
     * Uses the colorize method to apply the color. And adds the ANSI code for bold.
     * 
     * @param text  the text to color
     * @param color the ANSI color code
     * @return the colored and bold text
     */
    public static String colorizeAndBold(String text, String color) {
        return "\033[1m" + colorize(text, color);
        
    }
}
