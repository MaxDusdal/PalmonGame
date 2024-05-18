package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The TablePrinter class provides functionality for creating and printing tables in the console.
 * It dynamically adjusts column widths based on the content to ensure proper alignment.
 */
public class TablePrinter {
    private final List<String> headers;
    private final List<List<String>> rows;
    private final int[] columnWidths;

    /**
     * Constructs a TablePrinter with the specified headers.
     * 
     * @param headers a list of headers for the table
     */
    public TablePrinter(List<String> headers) {
        this.headers = headers;
        this.rows = new ArrayList<>();
        this.columnWidths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            columnWidths[i] = headers.get(i).length();
        }
    }

    /**
     * Adds a row to the table.
     * 
     * @param row a list of values for the row
     * @throws IllegalArgumentException if the row size does not match the header size
     */
    public void addRow(List<String> row) {
        if (row.size() != headers.size()) {
            throw new IllegalArgumentException("Row size does not match header size");
        }
        rows.add(row);
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i).length() > columnWidths[i]) {
                columnWidths[i] = row.get(i).length();
            }
        }
    }

    /**
     * Prints the table to the console.
     */
    public void print() {
        printLine();
        printRow(headers);
        printLine();
        for (List<String> row : rows) {
            printRow(row);
        }
        printLine();
    }

    /**
     * Prints a line separator based on the column widths.
     */
    private void printLine() {
        for (int width : columnWidths) {
            System.out.print("+");
            System.out.print("-".repeat(width + 2));
        }
        System.out.println("+");
    }

    /**
     * Prints a row of data with proper alignment based on the column widths.
     * 
     * @param row a list of values for the row
     */
    private void printRow(List<String> row) {
        for (int i = 0; i < row.size(); i++) {
            System.out.printf("| %-"+columnWidths[i]+"s ", row.get(i));
        }
        System.out.println("|");
    }
}
