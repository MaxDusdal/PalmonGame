package utils.ConsoleHelpers;

import data.Palmon;
import service.DataStorageService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.crypto.Data;

public class TableCreator {

    public static void printPalmons(List<Palmon> palmons) {
        List<String> headers = Stream
                .of("ID", "Name", "Primary Type", "Secondary Type", "Level", "Height", "Weight", "HP", "Attack",
                        "Defense", "Speed")
                .collect(Collectors.toList());
        TablePrinter tablePrinter = new TablePrinter(headers);

        for (Palmon palmon : palmons) {
            tablePrinter.addRow(Stream.of(
                    String.valueOf(palmon.getId()),
                    palmon.getName(),
                    palmon.getPrimaryType(),
                    palmon.getSecondaryType(),
                    String.valueOf(palmon.getLevel()),
                    String.valueOf(palmon.getHeight()),
                    String.valueOf(palmon.getWeight()),
                    String.valueOf(palmon.getHp()),
                    String.valueOf(palmon.getAttack()),
                    String.valueOf(palmon.getDefense()),
                    String.valueOf(palmon.getSpeed())).collect(Collectors.toList()));
        }

        tablePrinter.print();
    }

    public static void printPalmon(Palmon palmon) {
        List<String> headers = Stream
                .of("ID", "Name", "Primary Type", "Secondary Type", "Level", "Height", "Weight", "HP", "Attack",
                        "Defense", "Speed")
                .collect(Collectors.toList());
        TablePrinter tablePrinter = new TablePrinter(headers);

        tablePrinter.addRow(Stream.of(
                String.valueOf(palmon.getId()),
                palmon.getName(),
                palmon.getPrimaryType(),
                palmon.getSecondaryType(),
                String.valueOf(palmon.getLevel()),
                String.valueOf(palmon.getHeight()),
                String.valueOf(palmon.getWeight()),
                String.valueOf(palmon.getHp()),
                String.valueOf(palmon.getAttack()),
                String.valueOf(palmon.getDefense()),
                String.valueOf(palmon.getSpeed())).collect(Collectors.toList()));

        tablePrinter.print();
    }
}
