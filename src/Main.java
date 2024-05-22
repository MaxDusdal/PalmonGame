import data.Move;
import data.Palmon;
import data.Player;
import data.Team;
import service.DataIngestingService;
import utils.LocaleManager;
import utils.TeamBuilder;
import utils.ConsoleHelpers.TableCreator;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        // Initialize LocaleManager for language support, defaults to English
        LocaleManager.initialize();

        Player.initializeOrLoadProfile();

        // Load and store CSV files asynchronously
        CompletableFuture<Void> dataIngestion = DataIngestingService.loadAndStoreCSVFiles();

        dataIngestion.join(); // Wait for the async process to complete
        System.out.println(LocaleManager.getMessage("loading_completed", "Palmon"));

        Team userteam = TeamBuilder.createUserTeam();

        Team opponentTeam = TeamBuilder.createOpponentTeam();

    }
}
