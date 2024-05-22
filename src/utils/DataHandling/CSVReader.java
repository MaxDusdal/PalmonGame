package utils.DataHandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * The CSVReader class is responsible for reading CSV files and parsing their content using a provided DataParser.
 * Implements Runnable to allow concurrent execution.
 */
public class CSVReader implements Runnable {
    private final String path;
    private final DataParser parser;

    /**
     * Constructs a CSVReader with the specified file path and data parser.
     * 
     * @param path   the path to the CSV file
     * @param parser the data parser to process each line of the CSV file
     */
    public CSVReader(String path, DataParser parser) {
        this.path = path;
        this.parser = parser;
    }

    /**
     * Reads the CSV file, skips the header, and processes each line using the provided parser.
     * Time Complexity: O(n) where n is the number of lines in the CSV file
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
