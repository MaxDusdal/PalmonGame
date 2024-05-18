package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleManager {
    private static LocaleManager instance;
    private ResourceBundle messages;

    private LocaleManager(Locale locale) {
        String language = locale.getLanguage();
        try (FileInputStream fis = new FileInputStream("src/resources/Localization/messages_" + language + ".properties")) {
            this.messages = new PropertyResourceBundle(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized LocaleManager init(Locale locale) {
        if (instance == null || !instance.messages.getLocale().equals(locale)) {
            instance = new LocaleManager(locale);
        }
        return instance;
    }

    public static String getMessage(String key, Object... args) {
        if (instance == null) {
            throw new IllegalStateException("LocaleManager is not initialized. Call init(Locale) first.");
        }
        String pattern = instance.messages.getString(key);
        return MessageFormat.format(pattern, args);
    }

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
        LocaleManager.init(locale);
    }
}
