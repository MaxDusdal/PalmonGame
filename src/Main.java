import data.Battle;
import data.Player;
import data.Team;
import service.DataIngestingService;
import utils.LocaleManager;
import utils.TeamBuilder;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        // Load and store CSV files asynchronously
        CompletableFuture<Void> dataIngestion = DataIngestingService.loadAndStoreCSVFiles();

        // Initialize LocaleManager for language support, defaults to English
        LocaleManager.initialize();

        // Initialize or load the user profile
        Player.initializeOrLoadProfile();

        dataIngestion.join(); // Wait for the async process to complete

        Team userteam = TeamBuilder.createUserTeam();
        Team opponentTeam = TeamBuilder.createOpponentTeam();

        Battle battle = new Battle(userteam, opponentTeam);
        battle.startBattle();

    }
}
