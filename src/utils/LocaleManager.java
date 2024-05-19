package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;

import utils.ConsoleHelpers.InputManager;

/**
 * Manages the localization of the application.
 */
public class LocaleManager {
    private static LocaleManager instance;
    private ResourceBundle messages;
    private Locale currentLocale;


    /**
     * Creates a new LocaleManager with the given locale.
     * 
     * @param locale the locale to use, as a 2-letter language code
     */
    private LocaleManager(Locale locale) {
        this.currentLocale = locale;
        String language = locale.getLanguage();
        try (FileInputStream fis = new FileInputStream("src/resources/Localization/messages_" + language + ".properties")) {
            this.messages = new PropertyResourceBundle(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the LocaleManager with the given locale.
     * 
     * @param locale the locale to use, as a 2-letter language code
     * @return the LocaleManager instance
     */
    public static synchronized LocaleManager init(Locale locale) {
        if (instance == null || instance.messages == null || !instance.currentLocale.equals(locale)) {
            instance = new LocaleManager(locale);
        }
        return instance;
    }

    /**
     * Returns the message for the given key.
     * 
     * @param key the key of the message, as defined in the properties file
     * @param args the arguments to replace in the message
     * @return the formatted message
     */
    public static String getMessage(String key, Object... args) {
        if (instance == null) {
            throw new IllegalStateException("LocaleManager is not initialized. Call init(Locale) first.");
        }
        String pattern = instance.messages.getString(key);
        return MessageFormat.format(pattern, args);
    }

    /**
     * Initializes the LocaleManager with the default locale (English).
     * After initialization, the user is prompted to select a preferred locale.
     */
    public static void initialize() {
        // Initialize with default locale (English)
        Locale defaultLocale = Locale.ENGLISH;
        init(defaultLocale);
        
        // Query user for preferred locale
        updateUserLocale();
    }

    /**
     * Prompts the user to select a preferred locale.
     * The user can choose between English and German.
     */
    public static void updateUserLocale() {
        Integer selection = InputManager.SelectWithIndex(LocaleManager.getMessage("LANGUAGE_SELECT"), Map.of(1, "Englisch (default)", 2, "Deutsch"));
        Locale locale;
        switch (selection) {
            case 2:
                locale = Locale.GERMAN;
                break;
            case 1:
            default:
                locale = Locale.ENGLISH;
                break;
        }
        init(locale);

    }
}
