package utils;

import data.Palmon;
import service.DataStorageService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The TeamBuilder class provides functionality for creating teams of Palmons based on different criteria.
 */
public class TeamBuilder {

    /**
     * Builds a team of Palmons randomly.
     *
     * @param teamSize the size of the team
     * @return a list of randomly selected Palmons
     */
    public static List<Palmon> buildRandomTeam(int teamSize) {
        List<Palmon> allPalmons = DataStorageService.getPalmons();
        Collections.shuffle(allPalmons);
        return allPalmons.stream().limit(teamSize).collect(Collectors.toList());
    }

    /**
     * Builds a team of Palmons by specified IDs.
     *
     * @param ids the list of Palmon IDs to include in the team
     * @return a list of Palmons corresponding to the specified IDs
     */
    public static List<Palmon> buildTeamById(List<Integer> ids) {
        List<Palmon> allPalmons = DataStorageService.getPalmons();
        List<Palmon> team = allPalmons.stream()
                .filter(palmon -> ids.contains(palmon.getId()))
                .collect(Collectors.toList());
        validateTeamSize(team, ids.size());
        return team;
    }

    /**
     * Builds a team of Palmons by specified types.
     *
     * @param types the list of Palmon types to include in the team
     * @param teamSize the size of the team
     * @return a list of Palmons of the specified types
     */
    public static List<Palmon> buildTeamByType(List<String> types, int teamSize) {
        List<Palmon> allPalmons = DataStorageService.getPalmons();
        List<Palmon> filteredPalmons = allPalmons.stream()
                .filter(palmon -> types.contains(palmon.getPrimaryType()) || types.contains(palmon.getSecondaryType()))
                .collect(Collectors.toList());
        Collections.shuffle(filteredPalmons);
        List<Palmon> team = filteredPalmons.stream().limit(teamSize).collect(Collectors.toList());
        validateTeamSize(team, teamSize);
        return team;
    }

    /**
     * Builds a random team of Palmons for the opponent.
     *
     * @param teamSize the size of the opponent's team
     * @return a list of randomly selected Palmons for the opponent
     */
    public static List<Palmon> buildOpponentTeam(int teamSize) {
        return buildRandomTeam(teamSize);
    }

    /**
     * Ensures each Palmon in the team has only valid moves.
     *
     * @param team the list of Palmons in the team
     */
    public static void assignValidMoves(List<Palmon> team) {
        Map<Integer, Map<Integer, Integer>> palmonMoves = DataStorageService.getPalmonMoves();
        for (Palmon palmon : team) {
            Map<Integer, Integer> validMoves = palmonMoves.get(palmon.getId());
            if (validMoves != null) {
                for (Map.Entry<Integer, Integer> entry : validMoves.entrySet()) {
                    palmon.addMove(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private static void validateTeamSize(List<Palmon> team, int expectedSize) {
        if (team.size() != expectedSize) {
            System.out.println("Warning: Some IDs or types were invalid. Team size adjusted.");
        }
    }
}
