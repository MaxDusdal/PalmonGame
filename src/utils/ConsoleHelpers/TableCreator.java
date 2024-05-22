package utils.ConsoleHelpers;

import data.Move;
import data.Palmon;
import data.Team;
import utils.LocaleManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableCreator {

        /**
         * Prints a table of Palmons with their details.
         * Time Complexity: O(n) where n is the number of Palmons
         * 
         * @param palmons the list of Palmons to print
         */
        public static void printPalmons(List<Palmon> palmons) {
                List<String> headers = Stream
                                .of(
                                                LocaleManager.getMessage("TABLE_HEADER_ID"),
                                                LocaleManager.getMessage("TABLE_HEADER_NAME"),
                                                LocaleManager.getMessage("TABLE_HEADER_PRIMARY_TYPE"),
                                                LocaleManager.getMessage("TABLE_HEADER_SECONDARY_TYPE"),
                                                LocaleManager.getMessage("TABLE_HEADER_LEVEL"),
                                                LocaleManager.getMessage("TABLE_HEADER_HEIGHT"),
                                                LocaleManager.getMessage("TABLE_HEADER_WEIGHT"),
                                                "HP",
                                                LocaleManager.getMessage("TABLE_HEADER_ATTACK"),
                                                LocaleManager.getMessage("TABLE_HEADER_DEFENSE"),
                                                LocaleManager.getMessage("TABLE_HEADER_SPEED"))
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

        /**
         * Prints the details of a single Palmon.
         * Time Complexity: O(1)
         * 
         * @param palmon the Palmon to print
         */
        public static void printPalmon(Palmon palmon) {
                List<String> headers = Stream
                                .of(
                                                LocaleManager.getMessage("TABLE_HEADER_ID"),
                                                LocaleManager.getMessage("TABLE_HEADER_NAME"),
                                                LocaleManager.getMessage("TABLE_HEADER_PRIMARY_TYPE"),
                                                LocaleManager.getMessage("TABLE_HEADER_SECONDARY_TYPE"),
                                                LocaleManager.getMessage("TABLE_HEADER_LEVEL"),
                                                LocaleManager.getMessage("TABLE_HEADER_HEIGHT"),
                                                LocaleManager.getMessage("TABLE_HEADER_WEIGHT"),
                                                "HP",
                                                LocaleManager.getMessage("TABLE_HEADER_ATTACK"),
                                                LocaleManager.getMessage("TABLE_HEADER_DEFENSE"),
                                                LocaleManager.getMessage("TABLE_HEADER_SPEED"))
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

        /**
         * Prints a table of the fight moves for a given Palmon.
         * Time Complexity: O(n) where n is the number of moves
         * 
         * @param palmon the Palmon whose fight moves are to be printed
         */
        public static void printPalmonFightMoves(Palmon palmon) {
                System.out.println();
                System.out.println(ConsoleColors.colorizeAndBold(
                                LocaleManager.getMessage("TABLE_FIGHT_MOVES_FOR") + " " + palmon.getName(), "green"));

                List<String> headers = Stream.of(
                                LocaleManager.getMessage("TABLE_HEADER_NUMBER"),
                                LocaleManager.getMessage("TABLE_HEADER_NAME"),
                                LocaleManager.getMessage("TABLE_HEADER_DAMAGE"),
                                LocaleManager.getMessage("TABLE_HEADER_USAGES"),
                                LocaleManager.getMessage("TABLE_HEADER_ACCURACY"),
                                LocaleManager.getMessage("TABLE_HEADER_TYPE")).collect(Collectors.toList());
                TablePrinter tablePrinter = new TablePrinter(headers);
                int i = 0;

                for (Move move : palmon.getFightMoves()) {
                        i++;
                        tablePrinter.addRow(Stream.of(
                                        String.valueOf(i),
                                        move.getName(),
                                        String.valueOf(move.getDamage()),
                                        String.valueOf(move.getMaxUsages() - move.getUsages()) + "/"
                                                        + String.valueOf(move.getMaxUsages()),
                                        String.valueOf(move.getAccuracy()),
                                        move.getType()).collect(Collectors.toList()));
                }

                tablePrinter.print();
        }

        /**
         * Prints a table of the details of the Palmons in a team.
         * Time Complexity: O(n) where n is the number of Palmons in the team
         * 
         * @param team the team whose Palmons are to be printed
         */
        public static void printTeam(Team team) {
                List<String> headers = Stream.of(
                                LocaleManager.getMessage("TABLE_HEADER_NAME"),
                                LocaleManager.getMessage("TABLE_HEADER_PRIMARY_TYPE"),
                                LocaleManager.getMessage("TABLE_HEADER_SECONDARY_TYPE"),
                                LocaleManager.getMessage("TABLE_HEADER_LEVEL"),
                                LocaleManager.getMessage("TABLE_HEADER_HEIGHT"),
                                LocaleManager.getMessage("TABLE_HEADER_WEIGHT"),
                                "HP",
                                LocaleManager.getMessage("TABLE_HEADER_ATTACK"),
                                LocaleManager.getMessage("TABLE_HEADER_DEFENSE"),
                                LocaleManager.getMessage("TABLE_HEADER_SPEED"),
                                LocaleManager.getMessage("TABLE_HEADER_IS_DEFEATED")).collect(Collectors.toList());
                TablePrinter tablePrinter = new TablePrinter(headers);

                for (Palmon palmon : team.getPalmons()) {
                        String defeated = palmon.isDefeated() ? LocaleManager.getMessage("GENERAL_YES")
                                        : LocaleManager.getMessage("GENERAL_NO");
                        tablePrinter.addRow(Stream.of(
                                        palmon.getName(),
                                        palmon.getPrimaryType(),
                                        palmon.getSecondaryType(),
                                        String.valueOf(palmon.getLevel()),
                                        String.valueOf(palmon.getHeight()),
                                        String.valueOf(palmon.getWeight()),
                                        String.valueOf(palmon.getHp()),
                                        String.valueOf(palmon.getAttack()),
                                        String.valueOf(palmon.getDefense()),
                                        String.valueOf(palmon.getSpeed()),
                                        defeated).collect(Collectors.toList()));
                }

                tablePrinter.print();
        }
}
