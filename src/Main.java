import data.Palmon;
import service.DataIngestingService;
import service.DataStorageService;
import utils.LocaleManager;
import utils.TeamManager;
import utils.ConsoleHelpers.InputManager;
import utils.ConsoleHelpers.TableCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        // Load and store CSV files asynchronously
        CompletableFuture<Void> dataIngestion = DataIngestingService.loadAndStoreCSVFiles();
        
        // Initialize LocaleManager for language support
        LocaleManager.getLocaleFromUser();

        dataIngestion.join();  // Wait for the async process to complete
        System.out.println(LocaleManager.getMessage("loading_completed", "Palmon"));


        
        while (true) {
            /* 
            ArrayList<Integer> idList = InputManager.IntegerArray("Enter the IDs of Palmobs (comma-separated): ", DataStorageService.getPalmonIds());
            for (int id : idList) {
                Palmon palmon = DataStorageService.getPalmonById(id).orElse(null);
                if (palmon == null) {
                    System.out.println(LocaleManager.getMessage("palmon_not_found", id));
                } else {
                    System.out.println(palmon);
                }
            }
            */
            
            Integer selection = InputManager.SelectWithIndex("Bitte wähle wie du dein Team zusammenstellen willst?", Map.of(1, "Random", 2, "By ID", 3, "By Type"));

            System.out.println(selection);
        }
    }
}
