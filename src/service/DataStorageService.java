package service;

import data.Move;
import data.Palmon;
import utils.DataHandling.DataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataStorageService {
    private static ArrayList<Palmon> palmons = new ArrayList<>();
    private static CopyOnWriteArrayList<Move> moves = new CopyOnWriteArrayList<>();
    private static Map<Integer, Map<Integer, Integer>> palmonMoves = new ConcurrentHashMap<>();
    private static HashMap<String, HashMap<String, Float>> effectivity = new HashMap<>();

    private static final DataParser.PalmonParser palmonParser = new DataParser.PalmonParser();
    private static final DataParser.MoveParser moveParser = new DataParser.MoveParser();
    private static final DataParser.PalmonMoveParser palmonMoveParser = new DataParser.PalmonMoveParser();
    private static final DataParser.EffectivityParser effectivityParser = new DataParser.EffectivityParser();

    public static Optional<Palmon> getPalmonById(int palmonId) {
        return palmons.stream().filter(palmon -> palmon.getId() == palmonId).findFirst();
    }

    public static Optional<Move> getMoveById(int moveId) {
        return moves.stream().filter(move -> move.getId() == moveId).findFirst();
    }

    public static ArrayList<Palmon> getPalmons() {
        return palmons;
    }

    public static CopyOnWriteArrayList<Move> getMoves() {
        return moves;
    }

    public static Map<Integer, Map<Integer, Integer>> getPalmonMoves() {
        return palmonMoves;
    }

    public static HashMap<String, HashMap<String, Float>> getEffectivity() {
        return effectivity;
    }

    public static void assignData() {
        palmons = palmonParser.getData();
        moves = moveParser.getData();
        palmonMoves = palmonMoveParser.getData();
        effectivity = effectivityParser.getData();

        associateMovesWithPalmons();
    }

    private static Map<String, DataParser> getParsers() {
        Map<String, DataParser> parsers = new HashMap<>();
        parsers.put("palmon", palmonParser);
        parsers.put("moves", moveParser);
        parsers.put("palmon_move", palmonMoveParser);
        parsers.put("effectivity", effectivityParser);
        return parsers;
    }

    public static void associateMovesWithPalmons() {
        // Map for fast lookup of Palmons by their ID
        Map<Integer, Palmon> palmonMap = new HashMap<>();
        for (Palmon palmon : palmons) {
            palmonMap.put(palmon.getId(), palmon);
        }

        // Map for fast lookup of Moves by their ID
        Map<Integer, Move> moveMap = new HashMap<>();
        for (Move move : moves) {
            moveMap.put(move.getId(), move);
        }

        // Iterate through the palmonMoves map to associate Moves with Palmons
        for (Map.Entry<Integer, Map<Integer, Integer>> palmonEntry : palmonMoves.entrySet()) {
            int palmonId = palmonEntry.getKey();
            Palmon palmon = palmonMap.get(palmonId);

            if (palmon != null) {
                Map<Integer, Integer> movesForPalmon = palmonEntry.getValue();
                for (Map.Entry<Integer, Integer> moveEntry : movesForPalmon.entrySet()) {
                    int moveId = moveEntry.getKey();
                    int learnedOnLevel = moveEntry.getValue();
                    Move move = moveMap.get(moveId);

                    if (move != null) {
                        palmon.addMove(moveId, learnedOnLevel); // Use moveId and learnedOnLevel
                    }
                }
            }
        }
    }
}
