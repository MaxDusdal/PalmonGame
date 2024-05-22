package data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BattleHistory {
    private static final String HISTORY_FILE = "battle_history.csv";

    public static void saveBattleResult(Team playerTeam, Team opponentTeam, String result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ",");
            writer.write("PlayerTeam:" + playerTeam.getPalmons().toString() + ",");
            writer.write("OpponentTeam:" + opponentTeam.getPalmons().toString() + ",");
            writer.write("Result:" + result + "\n");
            System.out.println("Battle result saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving battle result: " + e.getMessage());
        }
    }

    public static void loadBattleHistory() {
        if (!Files.exists(Paths.get(HISTORY_FILE))) {
            System.out.println("No battle history found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error loading battle history: " + e.getMessage());
        }
    }
}
