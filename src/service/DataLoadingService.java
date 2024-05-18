package service;

import utils.DataHandling.CSVReader;
import utils.DataHandling.DataParser;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The DataLoadingService class provides static methods for loading CSV files
 * asynchronously using specified parsers.
 */
public class DataLoadingService {

    /**
     * Loads CSV files asynchronously using the provided parsers.
     * 
     * @param parsers a map of parser names to their corresponding DataParser instances
     * @return a CompletableFuture that completes when all files are loaded
     */
    public static CompletableFuture<Void> loadCSVFiles(Map<String, DataParser> parsers) {
        return CompletableFuture.runAsync(() -> {
            ExecutorService executor = Executors.newFixedThreadPool(parsers.size());
            try {
                for (Map.Entry<String, DataParser> parser : parsers.entrySet()) {
                    executor.submit(new CSVReader("src/resources/" + parser.getKey() + ".csv", parser.getValue()));
                }
                executor.shutdown();
                try {
                    if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    executor.shutdownNow();
                    System.out.println("File loading stopped.");
                }
            } catch (Exception e) {
                executor.shutdownNow();
                e.printStackTrace();
            }
        });
    }
}
