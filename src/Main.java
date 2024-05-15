import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import data.Move;
import data.Palmon;
import service.DataIngestingService;
import service.DataLoadingService;
import service.DataStorageService;
import service.DataIngestingService;

public class Main {
     public static void main(String[] args) {
        // Instantiate the services
        DataStorageService dataStorageService = new DataStorageService();
        DataLoadingService dataLoadingService = new DataLoadingService();
        DataIngestingService dataIngestingService = new DataIngestingService(dataStorageService, dataLoadingService);

        // Load and store the CSV files
        CompletableFuture<Void> dataIngestion = dataIngestingService.loadAndStoreCSVFiles();

        System.out.println("Welcome to Palmon!");


        // Wait for the data ingestion to complete
        dataIngestion.join();
        
        // Example of accessing loaded data
        int palmonId = 1; // Example ID
        System.out.println("Palmon with ID " + palmonId + ": " + dataStorageService.getPalmonById(palmonId));
        
        Palmon palmon = dataStorageService.getPalmonById(palmonId);
        Map<Integer, Integer> moves = palmon.getMoves();
        for (Map.Entry<Integer, Integer> move : moves.entrySet()) {
            System.out.println("Move ID: " + move.getKey() + ", Learned on level: " + move.getValue());
            Move moveData = dataStorageService.getMoveById(move.getKey());
            System.out.println("Move data: " + moveData);
            System.out.println("Move name: " + moveData.getName());
        }

        System.out.println("Moves for Palmon with ID " + palmonId + ": " + palmon.getMoves());

        int moveId = 1; // Example ID
        System.out.println("Move with ID " + moveId + ": " + dataStorageService.getMoveById(moveId));
    }
}