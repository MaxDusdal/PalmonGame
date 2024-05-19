package utils;

import data.Palmon;
import utils.ConsoleHelpers.InputManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TeamManager {

    public static List<Palmon> createUserTeam() {
        //int choice = InputManager.selectWithNumbers("Choose how to assemble your team:", Map.of(1, "Random", 2, "By ID", 3, "By Type"));
        int choice = 1;
        List<Palmon> userTeam = new ArrayList<>();
        switch (choice) {
            case 1:
                int teamSize = InputManager.Integer("Enter the number of Palmobs in your team: ");
                userTeam = TeamBuilder.buildRandomTeam(teamSize);
                break;
            case 2:
                ArrayList<Integer> idList = InputManager.IntegerArray("Enter the IDs of Palmobs (comma-separated): ");
                userTeam = TeamBuilder.buildTeamById(idList);
                break;
            case 3:
                System.out.print("Enter the types of Palmobs (comma-separated): ");
                /*
                String[] types = scanner.nextLine().split(",");
                List<String> typeList = new ArrayList<>();
                for (String type : types) {
                    typeList.add(type.trim());
                }
                System.out.print("Enter the number of Palmobs in your team: ");
                teamSize = scanner.nextInt();
                userTeam = TeamBuilder.buildTeamByType(typeList, teamSize);
                 */
                break;
            default:
                System.out.println("Invalid choice.");
                System.exit(1);
        }

        TeamBuilder.assignValidMoves(userTeam);
        return userTeam;
    }

    public static List<Palmon> createOpponentTeam() {
        int opponentTeamSize = InputManager.Integer("Enter the number of Palmobs for the opponent team (or 0 for random): ");
        if (opponentTeamSize == 0) {
            opponentTeamSize = new java.util.Random().nextInt(6) + 1; // Random team size between 1 and 6
        }
        List<Palmon> opponentTeam = TeamBuilder.buildOpponentTeam(opponentTeamSize);
        TeamBuilder.assignValidMoves(opponentTeam);
        return opponentTeam;
    }
}
