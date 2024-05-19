package utils.ConsoleHelpers;

import java.io.Console;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import utils.LocaleManager;

public class InputManager {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to select an option from a list of options.
     * 
     * @param prompt  the message to display to the user
     * @param options a map of options where the key is the value and the value is
     *                the option/description
     * @return the key of the selected option
     */
    public static String Select(String prompt, Map<String, String> options) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));

        options.forEach((key, value) -> System.out.printf("  %-10s : %s%n", key, value));
        System.out.println("=".repeat(prompt.length()));

        System.out.print(LocaleManager.getMessage("INPUT_SELECT_ENTER"));
        String selection = scanner.nextLine().trim();

        while (!options.containsKey(selection)) {
            System.out.print(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_SELECT_INVALID", options.keySet().toString()), "red"));
            selection = scanner.nextLine().trim();
        }

        return selection;
    }

    /**
     * Prompts the user to select an option from a list of options.
     * 
     * @param prompt  the message to display to the user
     * @param options a map of options where the key is the index and the value is
     *                the option/description
     * @return the key of the selected option
     */
    // TODO: Maybe switch to LinkedHashMap to prevent reordering of the options
    public static Integer SelectWithIndex(String prompt, Map<Integer, String> options) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));

        options.forEach((key, value) -> System.out.printf("  %d : %s%n", key, value));
        System.out.println("=".repeat(prompt.length()));

        System.out.print(LocaleManager.getMessage("INPUT_SELECT_ENTER"));
        int selection = 0;

        try {
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println(ConsoleColors.colorizeAndBold(
                    LocaleManager.getMessage("INPUT_SELECT_INVALID", options.keySet().toString()), "red"));
            scanner.nextLine(); // Clear the buffer
            return SelectWithIndex(prompt, options);
        }

        while (!options.containsKey(selection)) {
            System.out.print(ConsoleColors.colorizeAndBold(
                    LocaleManager.getMessage("INPUT_SELECT_INVALID", options.keySet().toString()), "red"));
            selection = scanner.nextInt();
            scanner.nextLine();
        }

        return selection;
    }

    /**
     * Prompts the user for a string input.
     * 
     * @param prompt the message to display to the user
     * @return a string input by the user
     */

    public static String String(String prompt) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_VALUE_ENTER"));
        return scanner.nextLine().trim();
    }

    /**
     * Prompts the user for an integer input.
     * Uses recursion to prompt the user again if the input is not an integer.
     * 
     * @param prompt the message to display to the user
     * @return an integer input by the user
     */
    public static int Integer(String prompt) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_VALUE_ENTER"));
        try {
            int input = scanner.nextInt();
            scanner.nextLine();
            return input;
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_INVALID"), "red"));
            scanner.nextLine(); // Clear the buffer
            return Integer(prompt);
        }
    }

    /**
     * Prompts the user for an integer input within a specified range.
     * Uses recursion to prompt the user again if the input is out of range.
     * 
     * @param prompt        the message to display to the user
     * @param lowerBoundary the lower boundary of the range (inclusive)£
     * @param upperBoundary the upper boundary of the range (inclusive)
     * @return a CompletableFuture that completes when the data is loaded and stored
     */
    public static int Integer(String prompt, Integer lowerBoundary, Integer upperBoundary) {
        Integer input = Integer(prompt);

        while (input < lowerBoundary || input > upperBoundary) {
            System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_OUT_OF_RANGE", lowerBoundary, upperBoundary), "red"));
            input = Integer(prompt);
        }

        return input;
    }

    /**
     * Prompts the user for a comma-separated list of integers.
     * Uses recursion to prompt the user again if the input is invalid.
     * 
     * @param prompt the message to display to the user
     * @returns a list of integers input by the user
     */
    public static ArrayList<Integer> IntegerArray(String prompt) {
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_VALUE_ENTER"));
        String input = scanner.nextLine().trim();

        // Check for empty input
        if (input.isEmpty()) {
            System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_ARRAY_EMPTY"), "red"));

            return IntegerArray(prompt);
        }

        String[] inputArray = input.split(",");
        ArrayList<Integer> intList = new ArrayList<>();

        try {
            for (String s : inputArray) {
                // Check for empty values within the array
                if (s.trim().isEmpty()) {
                    System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_INVALID"), "red"));
                    return IntegerArray(prompt);
                }
                intList.add(Integer.parseInt(s.trim()));
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_INVALID"), "red"));

            return IntegerArray(prompt);
        }

        return intList;
    }

    /**
     * Prompts the user for a comma-separated list of integers within a specified
     * range.
     * Uses recursion to prompt the user again if the input is invalid or out of
     * range.
     * 
     * @param prompt        the message to display to the user
     * @param lowerBoundary the lower boundary of the range (inclusive)
     * @param upperBoundary the upper boundary of the range (inclusive)
     * @returns a list of integers input by the user
     */
    public static ArrayList<Integer> IntegerArray(String prompt, Integer lowerBoundary, Integer upperBoundary) {
        ArrayList<Integer> input = IntegerArray(prompt);

        for (Integer i : input) {
            if (i < lowerBoundary || i > upperBoundary) {
                System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_OUT_OF_RANGE", lowerBoundary, upperBoundary), "red"));
                return IntegerArray(prompt, lowerBoundary, upperBoundary);
            }
        }

        return input;
    }

    /**
     * Prompts the user for a comma-separated list of integers within a specified
     * list of valid values.
     * Uses recursion to prompt the user again if the input is invalid or not in the
     * list of valid values.
     * 
     * @param prompt      the message to display to the user
     * @param validValues an ArrayList of valid values
     * @returns a list of integers input by the user
     */

    public static ArrayList<Integer> IntegerArray(String prompt, ArrayList<Integer> validValues) {
        ArrayList<Integer> input = IntegerArray(prompt);

        for (Integer i : input) {
            if (!validValues.contains(i)) {
                System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_ARRAY_NOT_IN_LIST"),"red"));
                return IntegerArray(prompt, validValues);
            }
        }

        return input;
    }
}
