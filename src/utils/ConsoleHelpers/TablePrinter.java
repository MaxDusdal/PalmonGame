package utils.ConsoleHelpers;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter {
    private final List<String> headers;
    private final List<List<String>> rows;
    private final int[] columnWidths;

    public TablePrinter(List<String> headers) {
        this.headers = headers;
        this.rows = new ArrayList<>();
        this.columnWidths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            columnWidths[i] = headers.get(i).length();
        }
    }
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

    public void print() {
        printLine();
        printRow(headers);
        printLine();
        for (List<String> row : rows) {
            printRow(row);
        }
        printLine();
    }

    private void printLine() {
        for (int width : columnWidths) {
            System.out.print("+");
            System.out.print("-".repeat(width + 2));
        }
        System.out.println("+");
    }

    private void printRow(List<String> row) {
        for (int i = 0; i < row.size(); i++) {
            System.out.printf("| %-"+columnWidths[i]+"s ", row.get(i));
        }
        System.out.println("|");
    }
}
