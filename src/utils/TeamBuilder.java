package utils;

import data.Palmon;
import service.DataStorageService;

import java.util.*;
import java.util.stream.Collectors;

public class TeamBuilder {

    public static List<Palmon> buildRandomTeam(int teamSize) {
        List<Palmon> allPalmons = DataStorageService.getPalmons();
        Collections.shuffle(allPalmons);
        return allPalmons.stream().limit(teamSize).collect(Collectors.toList());
    }

    public static List<Palmon> buildTeamById(List<Integer> ids) {
        List<Palmon> allPalmons = DataStorageService.getPalmons();
        List<Palmon> team = allPalmons.stream()
                .filter(palmon -> ids.contains(palmon.getId()))
                .collect(Collectors.toList());
        validateTeamSize(team, ids.size());
        return team;
    }

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

    public static List<Palmon> buildOpponentTeam(int teamSize) {
        return buildRandomTeam(teamSize);
    }

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
