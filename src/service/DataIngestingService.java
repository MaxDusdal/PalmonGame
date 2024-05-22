package service;

import java.util.concurrent.CompletableFuture;

/**
 * The DataIngestingService class provides static methods to load and store CSV data
 * by coordinating with the DataLoadingService and DataStorageService.
 */
public class DataIngestingService {

    /**
     * Loads and stores CSV files by utilizing DataLoadingService and DataStorageService.
     * 
     * @return a CompletableFuture that completes when the data is loaded and stored
     * Time Complexity: O(n)
     */
    public static CompletableFuture<Void> loadAndStoreCSVFiles() {
        return DataLoadingService.loadCSVFiles(DataStorageService.getParsers())
                .thenRun(DataStorageService::assignData);
    }
}
