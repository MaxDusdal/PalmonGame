package utils.DataHandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CSVReader implements Runnable {
    private final String path;
    private final DataParser parser;

    public CSVReader(String path, DataParser parser) {
        this.path = path;
        this.parser = parser;
    }

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
