package service;

import java.util.concurrent.CompletableFuture;

public class DataIngestingService {
    private final DataStorageService dataStorageService;
    private final DataLoadingService dataLoadingService;

    public DataIngestingService(DataStorageService dataStorageService, DataLoadingService dataLoadingService) {
        this.dataStorageService = dataStorageService;
        this.dataLoadingService = dataLoadingService;
    }

    public CompletableFuture<Void> loadAndStoreCSVFiles() {
        return DataLoadingService.loadCSVFiles(dataStorageService.getParsers())
                .thenRun(dataStorageService::assignData);
    }
}
