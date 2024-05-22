package utils.ConsoleHelpers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import utils.LocaleManager;

public class InputManager {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to select an option from a list of options.
     * Time Complexity: O(n) where n is the number of options
     * 
     * @param promptKey  the message to display to the user
     * @param options a map of options where the key is the value and the value is
     *                the option/description
     * @return the key of the selected option
     */
    public static String Select(String promptKey, Map<String, String> options) {
        String prompt = getPromptFromKey(promptKey);

        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));

        options.forEach((key, value) -> System.out.printf("  %-10s : %s%n", key, value));
        System.out.println("=".repeat(prompt.length()));

        System.out.print(LocaleManager.getMessage("INPUT_SELECT_ENTER"));
        String selection = scanner.nextLine().trim();

        while (!options.containsKey(selection)) {
            System.out.print(ConsoleColors.colorizeAndBold(
                    LocaleManager.getMessage("INPUT_SELECT_INVALID", options.keySet().toString()), "red"));
            selection = scanner.nextLine().trim();
        }

        return selection;
    }

    /**
     * Prompts the user to select an option from a list of options.
     * Time Complexity: O(n log n) where n is the number of options (due to sorting)
     * 
     * @param promptKey  the message to display to the user
     * @param options a map of options where the key is the index and the value is the option/description
     * @return the key of the selected option
     */
    public static Integer SelectWithIndex(String promptKey, Map<Integer, String> options) {
        String prompt = getPromptFromKey(promptKey);
        LinkedHashMap<Integer, String> sortedOptions = sortMapByKey(options);

        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));

        sortedOptions.forEach((key, value) -> System.out.printf("  %d : %s%n", key, value));
        System.out.println("=".repeat(prompt.length()));

        System.out.print(LocaleManager.getMessage("INPUT_SELECT_ENTER"));
        int selection = 0;

        try {
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println(ConsoleColors.colorizeAndBold(
                    LocaleManager.getMessage("INPUT_SELECT_INVALID", sortedOptions.keySet().toString()), "red"));
            scanner.nextLine(); // Clear the buffer
            return SelectWithIndex(promptKey, options);
        }

        while (!sortedOptions.containsKey(selection)) {
            System.out.print(ConsoleColors.colorizeAndBold(
                    LocaleManager.getMessage("INPUT_SELECT_INVALID", sortedOptions.keySet().toString()), "red"));
            selection = scanner.nextInt();
            scanner.nextLine();
        }

        return selection;
    }

    /**
     * Prompts the user for a string input.
     * Time Complexity: O(1)
     * 
     * @param promptKey the message to display to the user
     * @return a string input by the user
     */
    public static String String(String promptKey) {
        String prompt = getPromptFromKey(promptKey);
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_VALUE_ENTER"));
        return scanner.nextLine().trim();
    }

    /**
     * Prompts the user for a comma-separated list of strings.
     * Time Complexity: O(n) where n is the number of input strings
     * 
     * @param promptKey    the message to display to the user
     * @param validArrayList an ArrayList of valid values
     * @returns a list of strings input by the user
     */
    public static ArrayList<String> StringArray(String promptKey, ArrayList<String> validArrayList) {
        String prompt = getPromptFromKey(promptKey);
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_VALUE_ENTER"));
        String input = scanner.nextLine().trim();

        // Check for empty input
        if (input.isEmpty()) {
            System.out
                    .println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_ARRAY_EMPTY"), "red"));

            return StringArray(promptKey, validArrayList);
        }

        String[] inputArray = input.split(",");
        ArrayList<String> stringList = new ArrayList<>();

        for (String s : inputArray) {
            // Check for empty values within the array
            if (s.trim().isEmpty()) {
                System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_INVALID"), "red"));
                return StringArray(promptKey, validArrayList);
            }
            stringList.add(s.trim());
        }

        for (String s : stringList) {
            if (!validArrayList.contains(s)) {
                System.out.println(ConsoleColors
                        .colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_ARRAY_NOT_IN_LIST"), "red"));
                return StringArray(promptKey, validArrayList);
            }
        }

        return stringList;
    }

    /**
     * Prompts the user for an integer input.
     * Time Complexity: O(1)
     * 
     * @param promptKey the message to display to the user
     * @return an integer input by the user
     */
    public static int Integer(String promptKey) {
        String prompt = getPromptFromKey(promptKey);
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
            return Integer(promptKey);
        }
    }

    /**
     * Prompts the user for an integer input within a specified range.
     * Time Complexity: O(1)
     * 
     * @param promptKey    the message to display to the user
     * @param lowerBoundary the lower boundary of the range (inclusive)
     * @param upperBoundary the upper boundary of the range (inclusive)
     * @return an integer input by the user
     */
    public static int Integer(String promptKey, Integer lowerBoundary, Integer upperBoundary) {
        Integer input = Integer(promptKey);

        while (input < lowerBoundary || input > upperBoundary) {
            System.out.println(ConsoleColors.colorizeAndBold(
                    LocaleManager.getMessage("INPUT_VALUE_OUT_OF_RANGE", lowerBoundary, upperBoundary), "red"));
            input = Integer(promptKey);
        }

        return input;
    }

    /**
     * Prompts the user for a comma-separated list of integers.
     * Time Complexity: O(n) where n is the number of input integers
     * 
     * @param promptKey the message to display to the user
     * @returns a list of integers input by the user
     */
    public static ArrayList<Integer> IntegerArray(String promptKey) {
        String prompt = getPromptFromKey(promptKey);
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        System.out.print(LocaleManager.getMessage("INPUT_VALUE_ENTER"));
        String input = scanner.nextLine().trim();

        // Check for empty input
        if (input.isEmpty()) {
            System.out
                    .println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_ARRAY_EMPTY"), "red"));

            return IntegerArray(promptKey);
        }

        String[] inputArray = input.split(",");
        ArrayList<Integer> intList = new ArrayList<>();

        try {
            for (String s : inputArray) {
                // Check for empty values within the array
                if (s.trim().isEmpty()) {
                    System.out.println(
                            ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_INVALID"), "red"));
                    return IntegerArray(promptKey);
                }
                intList.add(Integer.parseInt(s.trim()));
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_INVALID"), "red"));

            return IntegerArray(promptKey);
        }

        return intList;
    }

    /**
     * Prompts the user for a comma-separated list of integers within a specified range.
     * Time Complexity: O(n) where n is the number of input integers
     * 
     * @param promptKey    the message to display to the user
     * @param lowerBoundary the lower boundary of the range (inclusive)
     * @param upperBoundary the upper boundary of the range (inclusive)
     * @returns a list of integers input by the user
     */
    public static ArrayList<Integer> IntegerArray(String promptKey, Integer lowerBoundary, Integer upperBoundary) {
        ArrayList<Integer> input = IntegerArray(promptKey);

        for (Integer i : input) {
            if (i < lowerBoundary || i > upperBoundary) {
                System.out.println(ConsoleColors.colorizeAndBold(
                        LocaleManager.getMessage("INPUT_VALUE_OUT_OF_RANGE", lowerBoundary, upperBoundary), "red"));
                return IntegerArray(promptKey, lowerBoundary, upperBoundary);
            }
        }

        return input;
    }

    /**
     * Prompts the user for a comma-separated list of integers within a specified list of valid values.
     * Time Complexity: O(n) where n is the number of input integers
     * 
     * @param promptKey   the message to display to the user
     * @param validValues an ArrayList of valid values
     * @returns a list of integers input by the user
     */
    public static ArrayList<Integer> IntegerArray(String promptKey, ArrayList<Integer> validValues) {
        ArrayList<Integer> input = IntegerArray(promptKey);

        for (Integer i : input) {
            if (!validValues.contains(i)) {
                System.out.println(ConsoleColors
                        .colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_ARRAY_NOT_IN_LIST"), "red"));
                return IntegerArray(promptKey, validValues);
            }
        }

        return input;
    }

    /**
     * Prompts the user to press Enter to continue.
     * Time Complexity: O(1)
     */
    public static void EnterToContinue() {
        System.out.print(LocaleManager.getMessage("INPUT_ENTER_TO_CONTINUE"));
        scanner.nextLine();
    }

    /**
     * Takes a map and sorts it by key in ascending order.
     * Time Complexity: O(n log n) where n is the number of entries in the map
     * 
     * @param <K> the type of the keys
     * @param <V> the type of the values
     * @param map the map to sort
     * @return a LinkedHashMap sorted by key
     */
    public static <K extends Comparable<K>, V> LinkedHashMap<K, V> sortMapByKey(Map<K, V> map) {
        // Create a new LinkedHashMap to store the sorted entries
        LinkedHashMap<K, V> sortedMap = new LinkedHashMap<>();

        // Get the map entries and sort them by key
        List<Map.Entry<K, V>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByKey());

        // Put the sorted entries into the LinkedHashMap
        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     * Uses the LocaleManager to get the prompt message from the key.
     * Time Complexity: O(1)
     * 
     * @param key the key to get the prompt message
     * @returns the prompt message
     */
    private static String getPromptFromKey(String key) {
        String prompt;
        try {
            prompt = LocaleManager.getMessage(key);
            if (prompt == null) {
                throw new Exception("Key not found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
            return key;
        }
        return prompt;
    }
}
