package data;

import java.util.Comparator;

public class Battle {
    private Team playerTeam;
    private Team opponentTeam;

    public Battle(Team playerTeam, Team opponentTeam) {
        this.playerTeam = playerTeam;
        this.opponentTeam = opponentTeam;
    }

    public void startBattle() {
        while (!isTeamDefeated(playerTeam) && !isTeamDefeated(opponentTeam)) {
            round();
        }

        if (isTeamDefeated(playerTeam)) {
            System.out.println("Opponent wins!");
        } else {
            System.out.println("Player wins!");
        }
    }

    private void round() {
        int round = 1;
        int playerTeamPalmonIndex = 0;
        int opponentTeamPalmonIndex = 0;

        Palmon playerPalmon = playerTeam.getPalmons().get(playerTeamPalmonIndex);
        Palmon opponentPalmon = opponentTeam.getPalmons().get(opponentTeamPalmonIndex);

        while (!playerPalmon.isDefeated() && !opponentPalmon.isDefeated()) {
            System.out.println("Round " + round++);
            System.out.println(playerPalmon.getName() + " vs. " + opponentPalmon.getName());
            attackSequence(playerPalmon, opponentPalmon);
        }

    }

    private void attackSequence(Palmon playerPalmon, Palmon opponentPalmon) {
        attack(playerPalmon, opponentPalmon);
        if (!opponentPalmon.isDefeated()) {
            attack(opponentPalmon, playerPalmon);
        }
    }

    private void attack(Palmon attacker, Palmon defender) {
        int damage = attacker.getAttack() - defender.getDefense();
        defender.attack(damage);
        System.out.println(attacker.getName() + " attacks " + defender.getName() + " for " + damage + " damage.");
    }

    private boolean isTeamDefeated(Team team) {
        for (Palmon palmon : team.getPalmons()) {
            if (!palmon.isDefeated()) {
                return false;
            }
        }
        return true;
    }
}
