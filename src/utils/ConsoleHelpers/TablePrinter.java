package utils.ConsoleHelpers;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter {
    private final List<String> headers;
    private final List<List<String>> rows;
    private final int[] columnWidths;

    /**
     * Creates a new TablePrinter with the given headers.
     * Time Complexity: O(n) where n is the number of headers
     * 
     * @param headers the headers of the table
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
     * Time Complexity: O(m) where m is the number of columns
     * 
     * @param row the row to add
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
     * Time Complexity: O(n * m) where n is the number of rows and m is the number of columns
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
     * Prints a line to the console. This is used to separate the table header, rows, and footer.
     * Time Complexity: O(m) where m is the number of columns
     */
    private void printLine() {
        for (int width : columnWidths) {
            System.out.print("+");
            System.out.print("-".repeat(width + 2));
        }
        System.out.println("+");
    }

    /**
     * Prints a row to the console.
     * Time Complexity: O(m) where m is the number of columns
     * 
     * @param row the row to print
     */
    private void printRow(List<String> row) {
        for (int i = 0; i < row.size(); i++) {
            System.out.printf("| %-" + columnWidths[i] + "s ", row.get(i));
        }
        System.out.println("|");
    }
}
