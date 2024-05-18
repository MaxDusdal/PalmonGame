package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * The CSVReader class implements the Runnable interface and is used to read and parse CSV files.
 * It processes each line of the CSV file using a provided DataParser.
 */
public class CSVReader implements Runnable {
    private final String path;
    private final DataParser parser;

    /**
     * Constructs a CSVReader with the specified path and parser.
     * 
     * @param path the path to the CSV file
     * @param parser the parser used to process each line of the CSV file
     */
    public CSVReader(String path, DataParser parser) {
        this.path = path;
        this.parser = parser;
    }

    /**
     * Reads the CSV file line by line, skipping the header, and processes each line
     * using the provided DataParser. This method is automatically invoked by an ExecutorService.
     */
    @Override
    public void run() {
        try (Stream<String> entries = Files.lines(Paths.get(path))) {
            entries.skip(1) // Skip header
                    .forEach(entry -> parser.processLine(entry.split(";")));
        } catch (IOException e) {
            System.out.println("@CSVReader: Error " + e.getMessage());
        }
    }
}
