package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * The LocaleManager class provides methods for managing and retrieving localized messages.
 * It uses a singleton pattern to ensure a single instance is used throughout the application.
 */
public class LocaleManager {
    private static LocaleManager instance;
    private ResourceBundle messages;

    /**
     * Private constructor to initialize the LocaleManager with the specified locale.
     * 
     * @param locale the Locale to be used for localization
     */
    private LocaleManager(Locale locale) {
        String language = locale.getLanguage();
        try (FileInputStream fis = new FileInputStream("src/resources/messages_" + language + ".properties")) {
            this.messages = new PropertyResourceBundle(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the LocaleManager with the specified locale.
     * If the LocaleManager is already initialized with the same locale, it returns the existing instance.
     * 
     * @param locale the Locale to be used for localization
     * @return the initialized LocaleManager instance
     */
    public static synchronized LocaleManager init(Locale locale) {
        if (instance == null || !instance.messages.getLocale().equals(locale)) {
            instance = new LocaleManager(locale);
        }
        return instance;
    }

    /**
     * Retrieves a localized message for the specified key and formats it with the given arguments.
     * 
     * @param key the key for the desired message
     * @param args the arguments to be used for formatting the message
     * @return the formatted localized message
     * @throws IllegalStateException if the LocaleManager is not initialized
     */
    public static String getMessage(String key, Object... args) {
        if (instance == null) {
            throw new IllegalStateException("LocaleManager is not initialized. Call init(Locale) first.");
        }
        String pattern = instance.messages.getString(key);
        return MessageFormat.format(pattern, args);
    }

    /**
     * Prompts the user to choose a language and initializes the LocaleManager with the chosen locale.
     */
    // TODO: Adjust the formatting to match the formatting of InputManager
    public static void getLocaleFromUser() {
        Scanner scanner = new Scanner(System.in);
        String prompt = "Choose language / Sprache wählen (en/de): ";
        System.out.println("\n\n" + prompt);
        System.out.println("=".repeat(prompt.length()));
        String language = scanner.nextLine().trim().toLowerCase();

        Locale locale;
        switch (language) {
            case "de":
                locale = Locale.GERMAN;
                break;
            case "en":
            default:
                locale = Locale.ENGLISH;
                break;
        }
        // Initialize the LocaleManager with the chosen locale
        LocaleManager.init(locale);
    }
}
