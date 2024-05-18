import utils.ConsoleHelper;
import utils.LocaleManager;
import service.DataIngestingService;
import service.DataStorageService;

import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {

        CompletableFuture<Void> dataIngestion = DataIngestingService.loadAndStoreCSVFiles();

        // Wait for the data ingestion to complete
        dataIngestion.join();  // .join() is used here to wait for the async process to complete

        ConsoleHelper.printPalmons(DataStorageService.getPalmons());
    }
}
