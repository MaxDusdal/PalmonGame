package utils.ConsoleHelpers;

import data.Palmon;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The ConsoleHelper class provides utility methods for printing data to the console.
 * It includes methods for printing lists of Palmons in a tabular format.
 */
public class TableCreator {

    /**
     * Prints a list of Palmons in a tabular format to the console.
     * 
     * @param palmons the list of Palmons to be printed
     */
    public static void printPalmons(List<Palmon> palmons) {
        List<String> headers = Stream.of("ID", "Name", "Primary Type", "Secondary Type", "Height", "Weight", "HP", "Attack", "Defense", "Speed")
                                     .collect(Collectors.toList());
        TablePrinter tablePrinter = new TablePrinter(headers);

        for (Palmon palmon : palmons) {
            tablePrinter.addRow(Stream.of(
                String.valueOf(palmon.getId()),
                palmon.getName(),
                palmon.getPrimaryType(),
                palmon.getSecondaryType(),
                String.valueOf(palmon.getHeight()),
                String.valueOf(palmon.getWeight()),
                String.valueOf(palmon.getHp()),
                String.valueOf(palmon.getAttack()),
                String.valueOf(palmon.getDefense()),
                String.valueOf(palmon.getSpeed())
            ).collect(Collectors.toList()));
        }

        tablePrinter.print();
    }

    /**
     * Prints the details of a single Palmon in a tabular format.
     *
     * @param palmon the Palmon to be printed
     */
    public static void printPalmon(Palmon palmon) {
        List<String> headers = Stream.of("ID", "Name", "Primary Type", "Secondary Type", "Height", "Weight", "HP", "Attack", "Defense", "Speed")
                                     .collect(Collectors.toList());
        TablePrinter tablePrinter = new TablePrinter(headers);

        tablePrinter.addRow(Stream.of(
            String.valueOf(palmon.getId()),
            palmon.getName(),
            palmon.getPrimaryType(),
            palmon.getSecondaryType(),
            String.valueOf(palmon.getHeight()),
            String.valueOf(palmon.getWeight()),
            String.valueOf(palmon.getHp()), 
            String.valueOf(palmon.getAttack()),
            String.valueOf(palmon.getDefense()),
            String.valueOf(palmon.getSpeed())
        ).collect(Collectors.toList()));

        tablePrinter.print();
    }

    public static void printPalmonMoves(Palmon palmon) {
        System.out.println("\nMoves:");
        System.out.println("=".repeat(6));
        for (Map.Entry<Integer, Integer> move : palmon.getMoves().entrySet()) {
            System.out.printf("Move ID: %d, Learned On Level: %d%n", move.getKey(), move.getValue());
        }
    }
}
