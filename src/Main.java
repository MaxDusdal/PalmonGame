import data.Palmon;
import service.DataIngestingService;
import utils.LocaleManager;
import utils.TeamManager;
import utils.ConsoleHelpers.TableCreator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        // Load and store CSV files asynchronously
        CompletableFuture<Void> dataIngestion = DataIngestingService.loadAndStoreCSVFiles();
        
        // Initialize LocaleManager for language support
        LocaleManager.getLocaleFromUser();

        dataIngestion.join();  // Wait for the async process to complete
        System.out.println(LocaleManager.getMessage("loading_completed", "Palmon"));

        // Create user team
        List<Palmon> userTeam = TeamManager.createUserTeam();

        // Generate opponent team
        List<Palmon> opponentTeam = TeamManager.createOpponentTeam();

        // Display teams
        System.out.println("\nYour Team:");
        TableCreator.printPalmons(userTeam);
        System.out.println("\nOpponent Team:");
        TableCreator.printPalmons(opponentTeam);
    }
}
