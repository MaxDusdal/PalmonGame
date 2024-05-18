package utils;

import data.Palmon;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The ConsoleHelper class provides utility methods for printing data to the console.
 * It includes methods for printing lists of Palmons in a tabular format.
 */
public class ConsoleHelper {

    /**
     * Prints a list of Palmons in a tabular format to the console.
     * 
     * @param palmons the list of Palmons to be printed
     */
    public static void printPalmons(List<Palmon> palmons) {
        List<String> headers = Stream.of("ID", "Name", "Primary Type", "Secondary Type", "Level")
                                     .collect(Collectors.toList());
        TablePrinter tablePrinter = new TablePrinter(headers);

        for (Palmon palmon : palmons) {
            tablePrinter.addRow(Stream.of(
                String.valueOf(palmon.getId()),
                palmon.getName(),
                palmon.getPrimaryType(),
                palmon.getSecondaryType(),
                String.valueOf(palmon.getLevel())
            ).collect(Collectors.toList()));
        }

        tablePrinter.print();
    }
}
