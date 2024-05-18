package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import data.Move;
import data.Palmon;

/**
 * The DataParser abstract class defines the structure for parsing data from CSV files.
 * It provides a method to process each line of the CSV and to retrieve the parsed data.
 */
public abstract class DataParser {

    /**
     * Processes a single line from the CSV file.
     * 
     * @param values an array of String values representing a line from the CSV file
     */
    abstract void processLine(String[] values);

    /**
     * Retrieves the parsed data.
     * 
     * @return an Object representing the parsed data
     */
    abstract Object getData();

    /**
     * Parses a numeric value from a String.
     * 
     * @param attribute the String to parse
     * @return the parsed integer value
     */
    private static int parseNumber(String attribute) {
        return Integer.parseInt(attribute);
    }

    /**
     * The EffectivityParser class extends DataParser to parse effectivity data from a CSV file.
     * It maps attacker types to defender types with their corresponding effectivity values.
     */
    public static class EffectivityParser extends DataParser {
        private final HashMap<String, HashMap<String, Float>> data = new HashMap<>();

        @Override
        public void processLine(String[] values) {
            String attackerType = values[0];
            String defenderType = values[1];
            float effectivity = Float.parseFloat(values[2].replace("%", "")) / 100;

            data.putIfAbsent(attackerType, new HashMap<>());
            data.get(attackerType).put(defenderType, effectivity);
        }

        @Override
        public HashMap<String, HashMap<String, Float>> getData() {
            return data;
        }
    }

    /**
     * The MoveParser class extends DataParser to parse move data from a CSV file.
     * It stores a list of Move objects.
     */
    public static class MoveParser extends DataParser {
        private final ArrayList<Move> data = new ArrayList<>();

        @Override
        public void processLine(String[] values) {
            int id = parseNumber(values[0]);
            String name = StringNormalizer.name(values[1]);
            int damage = parseNumber(values[2]);
            int maxUsages = parseNumber(values[3]);
            int accuracy = parseNumber(values[4]);
            String type = values[5];

            data.add(new Move(id, name, damage, maxUsages, accuracy, type));
        }

        @Override
        public CopyOnWriteArrayList<Move> getData() {
            return new CopyOnWriteArrayList<>(data);
        }
    }

    /**
     * The PalmonMoveParser class extends DataParser to parse the mapping of moves to Palmobs from a CSV file.
     * It maps Palmon IDs to their moves with the corresponding level at which the move is learned.
     */
    public static class PalmonMoveParser extends DataParser {
        private final Map<Integer, Map<Integer, Integer>> data = new HashMap<>();

        @Override
        public void processLine(String[] values) {
            int palmonID = parseNumber(values[0]);
            int moveID = parseNumber(values[1]);
            int learnedOnLevel = parseNumber(values[2]);

            data.putIfAbsent(palmonID, new HashMap<>());
            data.get(palmonID).put(moveID, learnedOnLevel);
        }

        @Override
        public Map<Integer, Map<Integer, Integer>> getData() {
            return data;
        }
    }

    /**
     * The PalmonParser class extends DataParser to parse Palmon data from a CSV file.
     * It stores a list of Palmon objects.
     */
    public static class PalmonParser extends DataParser {
        private final ArrayList<Palmon> data = new ArrayList<>();

        @Override
        public void processLine(String[] values) {
            int id = parseNumber(values[0]);
            String name = StringNormalizer.name(values[1]);
            int height = parseNumber(values[2]);
            int weight = parseNumber(values[3]);
            String type1 = values[4];
            String type2 = values[5];
            int hp = parseNumber(values[6]);
            int attack = parseNumber(values[7]);
            int defense = parseNumber(values[8]);
            int speed = parseNumber(values[9]);

            data.add(new Palmon(id, name, height, weight, type1, type2, hp, attack, defense, speed));
        }

        @Override
        public ArrayList<Palmon> getData() {
            return data;
        }
    }
}
