package data;

import service.DataStorageService;
import utils.ConsoleHelpers.ConsoleColors;
import utils.ConsoleHelpers.InputManager;
import utils.ConsoleHelpers.TableCreator;
import utils.LocaleManager;

import java.util.Map;
import java.util.Random;

public class Battle {
    private Team playerTeam;
    private Team opponentTeam;

    public Battle(Team playerTeam, Team opponentTeam) {
        this.playerTeam = playerTeam;
        this.opponentTeam = opponentTeam;
    }

    public void startBattle() {
        System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("BATTLE_START"), "green"));
        sleep(1000);

        while (!isTeamDefeated(playerTeam) && !isTeamDefeated(opponentTeam)) {
            round();
        }

        if (isTeamDefeated(playerTeam)) {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_RESULT_OPPONENT_WINS"), "red"));
            BattleHistory.saveBattleResult(playerTeam, opponentTeam, LocaleManager.getMessage("BATTLE_RESULT_OPPONENT_WINS"));
        } else {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_RESULT_PLAYER_WINS"), "green"));
            BattleHistory.saveBattleResult(playerTeam, opponentTeam, LocaleManager.getMessage("BATTLE_RESULT_PLAYER_WINS"));
        }
    }

    private void round() {
        int round = 1;
        int playerTeamPalmonIndex = 0;
        int opponentTeamPalmonIndex = 0;

        Palmon playerPalmon = playerTeam.getPalmons().get(playerTeamPalmonIndex);
        Palmon opponentPalmon = opponentTeam.getPalmons().get(opponentTeamPalmonIndex);

        while (!playerTeam.isDefeated() && !opponentTeam.isDefeated()) {
            while (!playerPalmon.isDefeated() && !opponentPalmon.isDefeated()) {
                printBattleStatus();
                System.out.println(ConsoleColors.colorizeAndBold("\n" + LocaleManager.getMessage("BATTLE_ROUND", round++), "cyan"));
                System.out.println(ConsoleColors.colorize(playerPalmon.getName(), "blue") + " vs. " + ConsoleColors.colorize(opponentPalmon.getName(), "red"));
                sleep(1000); // Add a delay before the attack sequence
                attackSequence(playerPalmon, opponentPalmon);
            }

            if (playerPalmon.isDefeated()) {
                System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_STATUS_DEFEATED", playerPalmon.getName()), "red"));
                playerTeamPalmonIndex++;
                if (playerTeamPalmonIndex < playerTeam.getPalmons().size()) {
                    playerPalmon = playerTeam.getPalmons().get(playerTeamPalmonIndex);
                    sleep(1000); // Add a delay before the next Palmon comes out
                }
            } else if (opponentPalmon.isDefeated()) {
                System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_STATUS_DEFEATED", opponentPalmon.getName()), "green"));
                opponentTeamPalmonIndex++;
                if (opponentTeamPalmonIndex < opponentTeam.getPalmons().size()) {
                    opponentPalmon = opponentTeam.getPalmons().get(opponentTeamPalmonIndex);
                    sleep(1000); // Add a delay before the next Palmon comes out
                }
            }
        }

        if (playerTeam.isDefeated()) {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_RESULT_PLAYER_TEAM_DEFEATED"), "red"));
        } else {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_RESULT_OPPONENT_TEAM_DEFEATED"), "green"));
        }
    }

    private void attackSequence(Palmon playerPalmon, Palmon opponentPalmon) {
        if (playerPalmon.getSpeed() >= opponentPalmon.getSpeed()) {
            attack(playerPalmon, opponentPalmon);
            if (!opponentPalmon.isDefeated()) {
                sleep(1000); // Add a delay before the counterattack
                attackByOpponent(opponentPalmon, playerPalmon);
            }
        } else {
            attackByOpponent(opponentPalmon, playerPalmon);
            if (!playerPalmon.isDefeated()) {
                sleep(1000); // Add a delay before the counterattack
                attack(playerPalmon, opponentPalmon);
            }
        }
    }

    private void attack(Palmon attacker, Palmon defender) {
        checkForMaxUsages(attacker);
        TableCreator.printPalmonFightMoves(attacker);
        int moveChoice = InputManager.Integer(LocaleManager.getMessage("BATTLE_CHOOSE_MOVE"), 1, attacker.getFightMoves().size());
        Move chosenMove = attacker.getFightMoves().get(moveChoice - 1);
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_USED", attacker.getName(), chosenMove.getName()), "magenta"));
        sleep(1000); // Add a delay before showing the move effect

        if (chosenMove.getAccuracy() < Math.random() * 100) {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_MISSED_ATTACK", attacker.getName()), "yellow"));
            return;
        }

        int damage = attacker.getAttack() + chosenMove.getDamage() - defender.getDefense();
        float effectivity = DataStorageService.getEffectivityMultiplier(attacker.getPrimaryType(), defender.getPrimaryType());
        damage *= effectivity;

        if (damage < 0) {
            damage = 0;
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_ATTACK_NOT_EFFECTIVE"), "yellow"));
            return;
        }

        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_DEALT", attacker.getName(), damage, defender.getName()), "green"));
        chosenMove.use();
        defender.attack(damage);
    }

    private void attackByOpponent(Palmon attacker, Palmon defender) {
        checkForMaxUsages(attacker);
        int moveChoice = (int) (Math.random() * attacker.getFightMoves().size());
        Move chosenMove = attacker.getFightMoves().get(moveChoice);
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_USED", attacker.getName(), chosenMove.getName()), "magenta"));
        sleep(1000); // Add a delay before showing the move effect

        if (chosenMove.getAccuracy() < Math.random() * 100) {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_MISSED_ATTACK", attacker.getName()), "yellow"));
            return;
        }

        int damage = attacker.getAttack() + chosenMove.getDamage() - defender.getDefense();
        float effectivity = DataStorageService.getEffectivityMultiplier(attacker.getPrimaryType(), defender.getPrimaryType());
        damage *= effectivity;

        if (damage < 0) {
            damage = 0;
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_ATTACK_NOT_EFFECTIVE"), "yellow"));
            return;
        }

        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_DEALT", attacker.getName(), damage, defender.getName()), "green"));
        chosenMove.use();
        defender.attack(damage);
    }

    private boolean isTeamDefeated(Team team) {
        return team.getPalmons().stream().allMatch(Palmon::isDefeated);
    }

    private void checkForMaxUsages(Palmon palmon) {
        palmon.getFightMoves().removeIf(move -> !move.isUsable());
    }

    private void printBattleStatus() {
        System.out.println();
        System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("BATTLE_STATUS"), "cyan"));
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_TEAM_STATUS", Player.getUserName()), "green"));
        TableCreator.printTeam(playerTeam);
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_TEAM_STATUS", Player.getOpponentName()), "red"));
        TableCreator.printTeam(opponentTeam);
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
