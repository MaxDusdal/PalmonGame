package utils;

import data.Palmon;
import data.Team;

import java.util.*;
import java.util.stream.Collectors;

public class TeamBuilder {
    private final List<Palmon> palmons;
    private static final Scanner scanner = new Scanner(System.in);

    public TeamBuilder(List<Palmon> palmons) {
        this.palmons = palmons;
    }

    public Team buildTeamRandomly(int teamSize) {
        Collections.shuffle(palmons);
        List<Palmon> teamPalmons = palmons.stream().limit(teamSize).collect(Collectors.toList());
        setTeamLevel(teamPalmons);
        return new Team(teamPalmons);
    }

    public Team buildTeamByType(String type, int teamSize) {
        List<Palmon> filteredPalmons = palmons.stream()
                .filter(palmon -> type.equalsIgnoreCase(palmon.getPrimaryType()) || type.equalsIgnoreCase(palmon.getSecondaryType()))
                .collect(Collectors.toList());
        List<Palmon> teamPalmons = filteredPalmons.stream().limit(teamSize).collect(Collectors.toList());
        setTeamLevel(teamPalmons);
        return new Team(teamPalmons);
    }

    public Team buildTeamById(List<Integer> ids) {
        List<Palmon> teamPalmons = palmons.stream()
                .filter(palmon -> ids.contains(palmon.getId()))
                .collect(Collectors.toList());
        setTeamLevel(teamPalmons);
        return new Team(teamPalmons);
    }

    private void setTeamLevel(List<Palmon> teamPalmons) {
        System.out.print("Enter the level for the Palmons: ");
        int level = scanner.nextInt();
        teamPalmons.forEach(palmon -> palmon.setLevel(level));
    }
}
