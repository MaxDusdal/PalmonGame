package utils;

import java.util.Map;
import java.util.Scanner;

/**
 * The InputManager class provides static methods for handling user input in the console.
 * It includes methods for selecting options, inputting strings, and inputting integers with validation.
 */
public class InputManager {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to select an option from the provided options and returns the selected key.
     * 
     * @param prompt the prompt message to display to the user
     * @param options a map of option keys and their corresponding descriptions
     * @return the key of the selected option
     */
    public static String select(String prompt, Map<String, String> options) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        
        options.forEach((key, value) -> System.out.printf("  %-10s : %s%n", key, value)); 
        System.out.println("=".repeat(prompt.length())); 
        
        System.out.print(LocaleManager.getMessage("INPUT_SELECT_CHOICE"));
        String selection = scanner.nextLine().trim();
        
        while (!options.containsKey(selection)) {
            System.out.print(LocaleManager.getMessage("INPUT_INVALID_CHOICE", options.keySet().toString()));
            selection = scanner.nextLine().trim();
        }
        
        return selection;
    }

    /**
     * Prompts the user to enter a string value and returns the input.
     * 
     * @param prompt the prompt message to display to the user
     * @return the input string from the user
     */
    public static String input(String prompt) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_ENTER_VALUE"));
        return scanner.nextLine().trim();
    }

    /**
     * Prompts the user to enter an integer value and returns the input.
     * Validates that the input is an integer.
     * 
     * @param prompt the prompt message to display to the user
     * @return the input integer from the user
     */
    public static int inputInt(String prompt) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_ENTER_VALUE"));
        while (!scanner.hasNextInt()) {
            System.out.print(LocaleManager.getMessage("INPUT_INVALID_VALUE"));
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        return value;
    }

    /**
     * Prompts the user to enter an integer value within the specified range and returns the input.
     * Validates that the input is an integer within the specified range.
     * 
     * @param prompt the prompt message to display to the user
     * @param min the minimum acceptable value (inclusive)
     * @param max the maximum acceptable value (inclusive)
     * @return the input integer from the user within the specified range
     */
    public static int inputRange(String prompt, int min, int max) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_ENTER_VALUE"));
        int value;
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.print(LocaleManager.getMessage("INPUT_INVALID_VALUE"));
                scanner.next();
            }
            value = scanner.nextInt();
            if (value >= min && value <= max) {
                break;
            }
            System.out.print(LocaleManager.getMessage("INPUT_OUT_OF_RANGE", min, max));
        }
        scanner.nextLine(); // consume the newline character
        return value;
    }
}
