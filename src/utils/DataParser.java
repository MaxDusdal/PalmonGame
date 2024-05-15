package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import data.Move;
import data.Palmon;

public abstract class DataParser {
    abstract void processLine(String[] values);

    abstract Object getData();

    private static int parseNumber(String attribute) {
        return Integer.parseInt(attribute);
    }

    public static class EffectivityParser extends DataParser {
        private final HashMap<String, HashMap<String, Float>> data = new HashMap<>();

        public void processLine(String[] values) {
            String attackerType = values[0];
            String defenderType = values[1];
            float effectivity = Float.parseFloat(values[2].replace("%", "")) / 100;

            data.putIfAbsent(attackerType, new HashMap<>());
            data.get(attackerType).put(defenderType, effectivity);
        }

        public HashMap<String, HashMap<String, Float>> getData() {
            return data;
        }
    }

    public static class MoveParser extends DataParser {
        private final ArrayList<Move> data = new ArrayList<>();

        public void processLine(String[] values) {
            int id = parseNumber(values[0]);
            String name = StringNormalizer.name(values[1]);
            int damage = parseNumber(values[2]);
            int maxUsages = parseNumber(values[3]);
            int accuracy = parseNumber(values[4]);
            String type = values[5];

            data.add(new Move(id, name, damage, maxUsages, accuracy, type));
        }

        public CopyOnWriteArrayList<Move> getData() {
            return new CopyOnWriteArrayList<>(data);
        }
    }

    public static class PalmonMoveParser extends DataParser {
        private final Map<Integer, Map<Integer, Integer>> data = new HashMap<>();

        @Override
        public void processLine(String[] values) {
            int palmonID = Integer.parseInt(values[0]);
            int moveID = Integer.parseInt(values[1]);
            int learnedOnLevel = Integer.parseInt(values[2]);

            data.putIfAbsent(palmonID, new HashMap<>());
            data.get(palmonID).put(moveID, learnedOnLevel);
        }

        @Override
        public Map<Integer, Map<Integer, Integer>> getData() {
            return data;
        }
    }

    public static class PalmonParser extends DataParser {
        private final ArrayList<Palmon> data = new ArrayList<>();

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

        public ArrayList<Palmon> getData() {
            return data;
        }

    }
}