package utils;

import data.Palmon;
import data.Team;
import service.DataStorageService;
import utils.ConsoleHelpers.InputManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

/**
 * The TeamBuilder class is responsible for creating teams for the user and the
 * opponent.
 */
public class TeamBuilder {
    private static final List<Palmon> availablePalmons = DataStorageService.getPalmons();
    private static int minLevel = 1;
    private static int maxLevel = 100;
    private static boolean customLevelRangeAsked = false;

    /**
     * Creates a team for the user. The user can choose to create a team randomly,
     * by ID, or by type.
     * 
     * @returns the user's team
     *          Time Complexity: O(n) where n is the number of Palmons available.
     */
    public static Team createUserTeam() {
        int choice = InputManager.SelectWithIndex("TEAM_ASSEMBLE_METHOD_QUESTION",
                Map.of(1, LocaleManager.getMessage("TEAM_ASSEMBLE_METHOD_RANDOM"),
                        2, LocaleManager.getMessage("TEAM_ASSEMBLE_METHOD_ID"),
                        3, LocaleManager.getMessage("TEAM_ASSEMBLE_METHOD_TYPE")));
        int teamSize = getTeamSize();

        switch (choice) {
            case 1:
                return buildRandomTeam(teamSize);
            case 2:
                List<Integer> idList = InputManager.IntegerArray("TEAM_ASSEMBLE_METHOD_ID_QUESTION",
                        DataStorageService.getPalmonIds());
                return buildTeamById(idList);
            case 3:
                List<String> types = InputManager.StringArray("TEAM_ASSEMBLE_METHOD_TYPE_QUESTION",
                        DataStorageService.getPalmonTypes());
                return buildTeamByType(types, teamSize);
            default:
                System.out.println(LocaleManager.getMessage("TEAM_ASSEMBLE_METHOD_INVALID"));
                return buildRandomTeam(teamSize);
        }
    }

    /**
     * Creates a team for the opponent. The user can choose to create a team
     * randomly or by specifying the size.
     * 
     * @return the opponent's team
     *         Time Complexity: O(n) where n is the number of Palmons available.
     */
    public static Team createOpponentTeam() {
        int opponentTeamSize = InputManager.Integer("TEAM_ASSEMBLE_AMOUNT_ENEMY", 0, 100);
        if (opponentTeamSize == 0) {
            opponentTeamSize = new Random().nextInt(6) + 1; // Random team size between 1 and 6
        }
        Team opponentTeam = buildRandomTeam(opponentTeamSize);
        setPalmonLevels(opponentTeam);
        assignPalmonFightMoves(opponentTeam);
        return opponentTeam;
    }

    /**
     * Builds a random team of the specified size.
     * 
     * @param size the size of the team
     * @return the team
     *         Time Complexity: O(k) where k is the size of the team.
     */
    private static Team buildRandomTeam(int size) {
        Team team = new Team();
        Random random = new Random();
        List<Palmon> shuffled = new ArrayList<>(availablePalmons);
        for (int i = 0; i < size; i++) {
            team.addPalmon(shuffled.get(random.nextInt(shuffled.size())));
        }
        setPalmonLevels(team);
        assignPalmonFightMoves(team);
        return team;
    }

    /**
     * Builds a team by the specified IDs.
     * 
     * @param ids the IDs of the Palmons
     * @return the team
     *         Time Complexity: O(k) where k is the number of IDs provided.
     */
    private static Team buildTeamById(List<Integer> ids) {
        Team team = new Team();
        for (Integer id : ids) {
            availablePalmons.stream()
                    .filter(palmon -> palmon.getId() == id)
                    .findFirst()
                    .ifPresent(team::addPalmon);
        }
        setPalmonLevels(team);
        assignPalmonFightMoves(team);
        return team;
    }

    /**
     * Builds a team by the specified types.
     * 
     * @param types the types of the Palmons
     * @param size  the size of the team
     * @return the team
     *         Time Complexity: O(n * k) where n is the number of Palmons available
     *         and k is the size of the team.
     */
    private static Team buildTeamByType(List<String> types, int size) {
        Team team = new Team();
        Random random = new Random();
        List<Palmon> filteredPalmons = availablePalmons.stream()
                .filter(palmon -> types.contains(palmon.getPrimaryType()) || types.contains(palmon.getSecondaryType()))
                .collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            team.addPalmon(filteredPalmons.get(random.nextInt(filteredPalmons.size())));
        }
        setPalmonLevels(team);
        assignPalmonFightMoves(team);
        return team;
    }

    /**
     * Gets the size of the team from the user.
     * 
     * @return the size of the team
     *         Time Complexity: O(1) for prompting the user.
     */
    private static int getTeamSize() {
        return InputManager.Integer("TEAM_ASSEMBLE_AMOUNT_USER", 1, 100);
    }

    /**
     * Sets the levels of the Palmons in the team.
     * 
     * @param team the team
     *             Time Complexity: O(k) where k is the number of Palmons in the
     *             team.
     */
    private static void setPalmonLevels(Team team) {
        if (shouldSetCustomLevelRange()) {
            ArrayList<Integer> levels = getPalmonLevels();
            minLevel = levels.get(0);
            maxLevel = levels.get(1);
        }
        Random random = new Random();
        for (Palmon palmon : team.getPalmons()) {
            int level = minLevel + random.nextInt(maxLevel - minLevel + 1);
            palmon.setLevel(level);
        }
    }

    /**
     * Gets the level range for the Palmons in the team.
     * 
     * @return the minimum and maximum levels
     *         Time Complexity: O(1) for prompting the user.
     */
    private static ArrayList<Integer> getPalmonLevels() {
        int min = InputManager.Integer("TEAM_ASSEMBLE_LEVEL_MIN_QUESTION", 1, 100);
        int max = InputManager.Integer("TEAM_ASSEMBLE_LEVEL_MAX_QUESTION", min, 100);
        return new ArrayList<>(List.of(min, max));
    }

    /**
     * Asks the user if they want to set a custom level range for the Palmons in the
     * team.
     * 
     * @return true if the user wants to set a custom level range, false otherwise
     *         Time Complexity: O(1) for prompting the user.
     */
    private static boolean shouldSetCustomLevelRange() {
        if (customLevelRangeAsked) {
            return false;
        }
        int choice = InputManager.SelectWithIndex("TEAM_ASSEMBLE_LEVEL_QUESTION",
                Map.of(1, LocaleManager.getMessage("GENERAL_YES"), 2,
                        LocaleManager.getMessage("TEAM_ASSEMBLE_LEVEL_NO")));
        customLevelRangeAsked = true;
        return choice == 1;
    }

    /**
     * Assigns fight moves to each Palmon in the team.
     * 
     * @param team the team
     * @return the team with assigned fight moves
     *         Time Complexity: O(k) where k is the number of Palmons in the team.
     */
    private static Team assignPalmonFightMoves(Team team) {
        for (Palmon palmon : team.getPalmons()) {
            palmon.setFightMoves();
        }
        return team;
    }
}
