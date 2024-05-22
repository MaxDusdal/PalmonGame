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

/**
 * A service class for storing data in memory.
 * The class is designed to store data in memory and provide access to it.
 */
public class DataStorageService {
    private static ArrayList<Palmon> palmons = new ArrayList<>();
    private static ArrayList<Integer> palmonIds = new ArrayList<>();
    private static ArrayList<String> palmonTypes = new ArrayList<>();
    private static CopyOnWriteArrayList<Move> moves = new CopyOnWriteArrayList<>();
    private static Map<Integer, Map<Integer, Integer>> palmonMoves = new ConcurrentHashMap<>();
    private static HashMap<String, HashMap<String, Float>> effectivity = new HashMap<>();

    private static final DataParser.PalmonParser palmonParser = new DataParser.PalmonParser();
    private static final DataParser.MoveParser moveParser = new DataParser.MoveParser();
    private static final DataParser.PalmonMoveParser palmonMoveParser = new DataParser.PalmonMoveParser();
    private static final DataParser.EffectivityParser effectivityParser = new DataParser.EffectivityParser();

    /**
     * Retrieves a Palmon by its ID.
     * Time Complexity: O(n)
     * @param palmonId the ID of the Palmon to retrieve
     * @return an Optional containing the Palmon if found, or empty if not found
     */
    public static Optional<Palmon> getPalmonById(int palmonId) {
        return palmons.stream().filter(palmon -> palmon.getId() == palmonId).findFirst();
    }

    /**
     * Retrieves a Move by its ID.
     * Time Complexity: O(n)
     * @param moveId the ID of the Move to retrieve
     * @return an Optional containing the Move if found, or empty if not found
     */
    public static Optional<Move> getMoveById(int moveId) {
        return moves.stream().filter(move -> move.getId() == moveId).findFirst();
    }

    /**
     * Retrieves all Palmobs.
     * Time Complexity: O(1)
     * @return a list of all Palmobs
     */
    public static ArrayList<Palmon> getPalmons() {
        return palmons;
    }

    /**
     * Retrieves all Palmon IDs.
     * Time Complexity: O(1)
     * @return a list of all Palmon IDs
     */
    public static ArrayList<Integer> getPalmonIds() {
        return palmonIds;
    }

    /**
     * Retrieves all Palmon types.
     * Time Complexity: O(1)
     * @return a list of all Palmon types
     */
    public static ArrayList<String> getPalmonTypes() {
        return palmonTypes;
    }

    /**
     * Retrieves all Moves.
     * Time Complexity: O(1)
     * @return a list of all Moves
     */
    public static CopyOnWriteArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * Retrieves the Palmon-Move associations.
     * Time Complexity: O(1)
     * @return a map of Palmon ID to their moves
     */
    public static Map<Integer, Map<Integer, Integer>> getPalmonMoves() {
        return palmonMoves;
    }

    /**
     * Retrieves the effectivity multipliers.
     * Time Complexity: O(1)
     * @return a map of effectivity multipliers
     */
    public static HashMap<String, HashMap<String, Float>> getEffectivity() {
        return effectivity;
    }

    /**
     * Returns an effectivity multiplier for the given attacking and defending types.
     * Time Complexity: O(1)
     * @param attackingType the type of the attacking Palmon
     * @param defendingType the type of the defending Palmon
     * @return the effectivity multiplier
     */
    public static float getEffectivityMultiplier(String attackingType, String defendingType) {
        return effectivity.get(attackingType).get(defendingType);
    }
    
    /**
     * Returns a map of parsers for the different data types.
     * The key is the name of the data type and the value is the parser for that data type.
     * Time Complexity: O(1)
     * @return a map of parsers
     */
    public static Map<String, DataParser> getParsers() {
        Map<String, DataParser> parsers = new HashMap<>();
        parsers.put("palmon", palmonParser);
        parsers.put("moves", moveParser);
        parsers.put("palmon_move", palmonMoveParser);
        parsers.put("effectivity", effectivityParser);
        return parsers;
    }
    
    /**
     * Assigns the data from the parsers to the respective data structures.
     * This method should be called after the data has been loaded.
     * Time Complexity: O(n)
     */
    public static void assignData() {
        palmons = palmonParser.getData();
        moves = moveParser.getData();
        palmonMoves = palmonMoveParser.getData();
        effectivity = effectivityParser.getData();

        extractPalmonIds();
        extractPalmonTypes();
        associateMovesWithPalmons();
    }

    /**
     * Extracts the IDs of all Palmobs and stores them in a list.
     * The list is used for quick access to Palmobs by ID.
     * Time Complexity: O(n)
     */
    private static void extractPalmonIds() {
        for (Palmon palmon : palmons) {
            palmonIds.add(palmon.getId());
        }
    }

    /**
     * Extracts the types of all Palmobs and stores them in a list. Doesn't store duplicates.
     * Time Complexity: O(n)
     */
    private static void extractPalmonTypes() {
        for (Palmon palmon : palmons) {
            if (!palmonTypes.contains(palmon.getPrimaryType())) {
                palmonTypes.add(palmon.getPrimaryType());
            }
            if (!palmonTypes.contains(palmon.getSecondaryType())) {
                palmonTypes.add(palmon.getSecondaryType());
            }
        }
    }

    /**
     * Associates moves with Palmobs based on the data from the Palmon-Move parser.
     * Time Complexity: O(n + m) where n is the number of Palmobs and m is the number of Moves
     */
    private static void associateMovesWithPalmons() {
        Map<Integer, Palmon> palmonMap = new HashMap<>();
        for (Palmon palmon : palmons) {
            palmonMap.put(palmon.getId(), palmon);
        }

        Map<Integer, Move> moveMap = new HashMap<>();
        for (Move move : moves) {
            moveMap.put(move.getId(), move);
        }

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
                        palmon.addMove(moveId, learnedOnLevel);
                    }
                }
            }
        }
    }
}
