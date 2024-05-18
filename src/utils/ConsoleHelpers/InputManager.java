package utils.ConsoleHelpers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import utils.LocaleManager;

public class InputManager {
    private static final Scanner scanner = new Scanner(System.in);

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

    public static int selectWithNumbers(String prompt, Map<Integer, String> options) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        
        options.forEach((key, value) -> System.out.printf("  %-10s : %s%n", key, value)); 
        System.out.println("=".repeat(prompt.length())); 
        
        System.out.print(LocaleManager.getMessage("INPUT_SELECT_CHOICE"));
        int selection = inputInt("Enter your choice: ");
        
        while (!options.containsKey(selection)) {
            System.out.print(LocaleManager.getMessage("INPUT_INVALID_CHOICE", options.keySet().toString()));
            selection = inputInt("Enter your choice: ");
        }
        
        return selection;
    }

    public static String input(String prompt) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_ENTER_VALUE"));
        return scanner.nextLine().trim();
    }

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

    public static ArrayList<Integer> inputIntArray(String prompt) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_ENTER_VALUE"));
        String[] values = scanner.nextLine().split(",");
        ArrayList<Integer> intValues = new ArrayList<>();
        for (String value : values) {
            try {
                intValues.add(Integer.parseInt(value.trim()));
            } catch (NumberFormatException e) {
                System.out.println(LocaleManager.getMessage("INPUT_INVALID_VALUE"));
            }
        }
        return intValues;
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
