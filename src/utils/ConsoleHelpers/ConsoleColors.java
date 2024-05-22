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
     * Time Complexity: O(1)
     * @param color the ANSI color code
     * @param text  the text to color
     * @return the colored text
     */
    public static String colorize(String text, String color) {
        String colorCode;
        switch (color.toLowerCase()) {
            case "red":
                colorCode = RED;
                break;
            case "green":
                colorCode = GREEN;
                break;
            case "yellow":
                colorCode = YELLOW;
                break;
            case "blue":
                colorCode = BLUE;
                break;
            case "magenta":
                colorCode = MAGENTA;
                break;
            case "cyan":
                colorCode = CYAN;
                break;
            case "white":
                colorCode = WHITE;
                break;
            default:
                throw new IllegalArgumentException("Invalid color");
        }
        return colorCode + text + RESET;
    }

    /**
     * Returns the given text in the specified color and bold.
     * Uses the colorize method to apply the color and adds the ANSI code for bold.
     * Time Complexity: O(1)
     * @param text  the text to color
     * @param color the ANSI color code
     * @return the colored and bold text
     */
    public static String colorizeAndBold(String text, String color) {
        return "\033[1m" + colorize(text, color);
    }
}
