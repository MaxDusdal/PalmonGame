package data;

import utils.LocaleManager;
import utils.ConsoleHelpers.InputManager;
import utils.DataHandling.StringNormalizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The Player class manages the names of the user and the opponent.
 */
public class Player {
    private static String userName;
    private static String opponentName;
    private static final String PROFILE_FILE = "player_profiles.csv";

    /**
     * Sets the name of the user.
     *
     * @param name the name of the user
     * Time Complexity: O(1)
     */
    public static void setUserName(String name) {
        userName = name;
    }

    /**
     * Sets the name of the opponent.
     *
     * @param name the name of the opponent
     * Time Complexity: O(1)
     */
    public static void setOpponentName(String name) {
        opponentName = name;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     * Time Complexity: O(1)
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Gets the name of the opponent.
     *
     * @return the name of the opponent
     * Time Complexity: O(1)
     */
    public static String getOpponentName() {
        return opponentName;
    }

    /**
     * Prompts the user to enter names for themselves and their opponent.
     * Time Complexity: O(1)
     */
    public static void initializeNames() {
        userName = InputManager.String("PLAYER_NAME_QUESTION", 1, 100);
        userName = StringNormalizer.name(userName);
        opponentName = InputManager.String("PLAYER_ENEMY_NAME_QUESTION", 1, 100);
        opponentName = StringNormalizer.name(opponentName);
    }

    /**
     * Saves the player names to a CSV file.
     * Time Complexity: O(1)
     */
    public static void saveProfile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROFILE_FILE, false))) {
            writer.write("Timestamp,UserName,OpponentName\n");
            writer.write(System.currentTimeMillis() + "," + userName + "," + opponentName + "\n");
        } catch (IOException e) {
            System.err.println("Error saving profile: " + e.getMessage());
        }
    }

    /**
     * Loads the player names from a CSV file.
     *
     * @return true if the profile was loaded successfully, false otherwise
     * Time Complexity: O(n) where n is the number of lines in the CSV file
     */
    public static boolean loadProfile() {
        if (!Files.exists(Paths.get(PROFILE_FILE))) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(PROFILE_FILE))) {
            List<String> lines = reader.lines().skip(1).toList(); // Skip the header line
            if (!lines.isEmpty()) {
                String[] data = lines.get(0).split(",");
                userName = data[1];
                opponentName = data[2];
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error loading profile: " + e.getMessage());
        }
        return false;
    }

    /**
     * Reads the profile information from the CSV file.
     *
     * @return the profile information as a string
     * Time Complexity: O(n) where n is the number of lines in the CSV file
     */
    public static String getProfileInfo() {
        if (!Files.exists(Paths.get(PROFILE_FILE))) {
            return "No saved profile found.";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(PROFILE_FILE))) {
            List<String> lines = reader.lines().skip(1).toList(); // Skip the header line
            if (!lines.isEmpty()) {
                String[] data = lines.get(0).split(",");
                long timestamp = Long.parseLong(data[0]);
                String userName = data[1];

                String formattedDate = new SimpleDateFormat("dd.MM.yy HH:mm").format(new Date(timestamp));
                return "-> " + userName + " @ (" + formattedDate + ")";
            }
        } catch (IOException e) {
            return "Error reading profile: " + e.getMessage();
        }
        return "No valid profile found.";
    }

    /**
     * Prompts the user to decide whether to load an existing profile or create a new one.
     * Time Complexity: O(1)
     */
    public static void initializeOrLoadProfile() {
        if (Files.exists(Paths.get(PROFILE_FILE))) {
            int choice = InputManager.SelectWithIndex("PROFILE_LOADING_QUESTION",
                    Map.of(1, LocaleManager.getMessage("PLAYER_SELECT_LOAD") + getProfileInfo(), 2, LocaleManager.getMessage("PLAYER_SELECT_CREATE")));
            if (choice == 1) {
                if (loadProfile()) {
                    return;
                }
            }
        }
        initializeNames();
        saveProfile();
    }
}
