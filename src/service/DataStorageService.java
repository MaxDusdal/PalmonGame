package service;

import data.Move;
import data.Palmon;
import utils.DataParser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataStorageService {
    private ArrayList<Palmon> palmons;
    private CopyOnWriteArrayList<Move> moves;
    private Map<Integer, Map<Integer, Integer>> palmonMoves = new ConcurrentHashMap<>();
    private HashMap<String, HashMap<String, Float>> effectivity = new HashMap<>();

    // Initialize the parsers
    private final DataParser.PalmonParser palmonParser = new DataParser.PalmonParser();
    private final DataParser.MoveParser moveParser = new DataParser.MoveParser();
    private final DataParser.PalmonMoveParser palmonMoveParser = new DataParser.PalmonMoveParser();
    private final DataParser.EffectivityParser effectivityParser = new DataParser.EffectivityParser();

    public Optional<Palmon> getPalmonById(int palmonId) {
        return palmons.stream().filter(palmon -> palmon.getId() == palmonId).findFirst();
    }

    public Optional<Move> getMoveById(int moveId) {
        return moves.stream().filter(move -> move.getId() == moveId).findFirst();
    }

    // Assign the data to the fields
    public void assignData() {
        this.palmons = palmonParser.getData();
        this.moves = moveParser.getData();
        this.palmonMoves = palmonMoveParser.getData();
        this.effectivity = effectivityParser.getData();

        associateMovesWithPalmons();
    }

    // Initialize parsers, allows us to extend the functionality to add more parsers (not needed in this project but good practice)
    private Map<String, DataParser> initializeParsers() {
        Map<String, DataParser> parsers = new HashMap<>();
        parsers.put("palmon", palmonParser);
        parsers.put("moves", moveParser);
        parsers.put("palmon_move", palmonMoveParser);
        parsers.put("effectivity", effectivityParser);
        return parsers;
    }

    // Get the parsers, used in DataLoadingService to load the correct csv and parse with the right parser
    public Map<String, DataParser> getParsers() {
        return initializeParsers();
    }

    // run after data is loaded to associate moves with palmons, gets stored in the palmon object
    public void associateMovesWithPalmons() {
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
