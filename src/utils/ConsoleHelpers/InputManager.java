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
     * 
     * @param prompt  the message to display to the user
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
     * 
     * @param prompt  the message to display to the user
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
            return SelectWithIndex(prompt, options);
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
     * 
     * @param prompt the message to display to the user
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

            return StringArray(prompt, validArrayList);
        }

        String[] inputArray = input.split(",");
        ArrayList<String> stringList = new ArrayList<>();

        for (String s : inputArray) {
            // Check for empty values within the array
            if (s.trim().isEmpty()) {
                System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_INVALID"), "red"));
                return StringArray(prompt, validArrayList);
            }
            stringList.add(s.trim());
        }

        for (String s : stringList) {
            if (!validArrayList.contains(s)) {
                System.out.println(ConsoleColors
                        .colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_ARRAY_NOT_IN_LIST"), "red"));
                return StringArray(prompt, validArrayList);
            }
        }

        return stringList;
    }

    /**
     * Prompts the user for an integer input.
     * Uses recursion to prompt the user again if the input is not an integer.
     * 
     * @param prompt the message to display to the user
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
     * Uses recursion to prompt the user again if the input is invalid.
     * 
     * @param prompt the message to display to the user
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

            return IntegerArray(prompt);
        }

        String[] inputArray = input.split(",");
        ArrayList<Integer> intList = new ArrayList<>();

        try {
            for (String s : inputArray) {
                // Check for empty values within the array
                if (s.trim().isEmpty()) {
                    System.out.println(
                            ConsoleColors.colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_INVALID"), "red"));
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
    public static ArrayList<Integer> IntegerArray(String promptKey, Integer lowerBoundary, Integer upperBoundary) {
        String prompt = getPromptFromKey(promptKey);
        ArrayList<Integer> input = IntegerArray(prompt);

        for (Integer i : input) {
            if (i < lowerBoundary || i > upperBoundary) {
                System.out.println(ConsoleColors.colorizeAndBold(
                        LocaleManager.getMessage("INPUT_VALUE_OUT_OF_RANGE", lowerBoundary, upperBoundary), "red"));
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

    public static ArrayList<Integer> IntegerArray(String promptKey, ArrayList<Integer> validValues) {
        String prompt = getPromptFromKey(promptKey);
        ArrayList<Integer> input = IntegerArray(prompt);

        for (Integer i : input) {
            if (!validValues.contains(i)) {
                System.out.println(ConsoleColors
                        .colorizeAndBold(LocaleManager.getMessage("INPUT_VALUE_ARRAY_NOT_IN_LIST"), "red"));
                return IntegerArray(prompt, validValues);
            }
        }

        return input;
    }

    /**
     * Takes a map and sorts it by key in ascending order.
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
            return key;
        }
        return prompt;
    }
}
