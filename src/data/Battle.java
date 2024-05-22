package data;

import service.DataStorageService;
import utils.ConsoleHelpers.ConsoleColors;
import utils.ConsoleHelpers.InputManager;
import utils.ConsoleHelpers.TableCreator;
import utils.LocaleManager;

public class Battle {
    private Team playerTeam;
    private Team opponentTeam;

    public Battle(Team playerTeam, Team opponentTeam) {
        this.playerTeam = playerTeam;
        this.opponentTeam = opponentTeam;
    }

    /**
     * Starts the battle between the player's team and the opponent's team.
     * The battle continues until one of the teams is defeated.
     * The battle result is saved in the battle history.
     * Time Complexity: O(n)
     */
    public void startBattle() {
        System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("BATTLE_START"), "green"));
        sleep(2000);

        while (!isTeamDefeated(playerTeam) && !isTeamDefeated(opponentTeam)) {
            round();
        }

        if (isTeamDefeated(playerTeam)) {
            System.out.println();
            System.out.println();
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_RESULT_OPPONENT_WINS", Player.getOpponentName()), "red"));
            System.out.println("I've failed over and over and over again in my life. And that is why I succeed. - Michael Jordan");
        } else {
            System.out.println();
            System.out.println();
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_RESULT_PLAYER_WINS", Player.getUserName()), "green"));
            TableCreator.printTeam(playerTeam);

        }
    }

    /**
     * Executes a round of the battle.
     * A round consists of a sequence of attacks between the player's team and the opponent's team.
     * The round continues until one of the teams is defeated.
     * The battle status is printed at the beginning of each round.
     * Time Complexity: O(n^2)
     */
    private void round() {
        int round = 1;
        int playerTeamPalmonIndex = 0;
        int opponentTeamPalmonIndex = 0;

        Palmon playerPalmon = playerTeam.getPalmons().get(playerTeamPalmonIndex);
        Palmon opponentPalmon = opponentTeam.getPalmons().get(opponentTeamPalmonIndex);

        while (!playerTeam.isDefeated() && !opponentTeam.isDefeated()) {
            while (!playerPalmon.isDefeated() && !opponentPalmon.isDefeated()) {
                System.out.println();
                System.out.println(ConsoleColors.colorizeAndBold("\n" + LocaleManager.getMessage("BATTLE_ROUND", round++), "cyan"));
                System.out.println(ConsoleColors.colorize(playerPalmon.getName(), "blue") + " vs. " + ConsoleColors.colorize(opponentPalmon.getName(), "red"));
                sleep(2000); // Add a delay before the attack sequence
                attackSequence(playerPalmon, opponentPalmon);
            }

            if (playerPalmon.isDefeated()) {
                System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_STATUS_DEFEATED", playerPalmon.getName()), "red"));
                playerTeamPalmonIndex++;
                if (playerTeamPalmonIndex < playerTeam.getPalmons().size()) {
                    playerPalmon = playerTeam.getPalmons().get(playerTeamPalmonIndex);
                    sleep(2000); // Add a delay before the next Palmon comes out
                }
            } else if (opponentPalmon.isDefeated()) {
                System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_STATUS_DEFEATED", opponentPalmon.getName()), "green"));
                opponentTeamPalmonIndex++;
                if (opponentTeamPalmonIndex < opponentTeam.getPalmons().size()) {
                    opponentPalmon = opponentTeam.getPalmons().get(opponentTeamPalmonIndex);
                    sleep(2000); // Add a delay before the next Palmon comes out
                }
            }
        }

        if (playerTeam.isDefeated()) {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_RESULT_PLAYER_TEAM_DEFEATED", Player.getUserName()), "red"));
        } else {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_RESULT_OPPONENT_TEAM_DEFEATED", Player.getOpponentName()), "green"));
        }
    }

    /**
     * Executes a sequence of attacks between the player's Palmon and the opponent's Palmon.
     * The attack sequence continues until one of the Palmons is defeated.
     * The attack sequence consists of the player's Palmon attacking first, followed by the opponent's Palmon.
     * @param playerPalmon the currently active Palmon of the player
     * @param opponentPalmon the currently active Palmon of the opponent
     * Time Complexity: O(1)
     */
    private void attackSequence(Palmon playerPalmon, Palmon opponentPalmon) {
        if (playerPalmon.getSpeed() >= opponentPalmon.getSpeed()) {
            attack(playerPalmon, opponentPalmon);
            if (!opponentPalmon.isDefeated()) {
                sleep(2000); // Add a delay before the counterattack
                attackByOpponent(opponentPalmon, playerPalmon);
            }
        } else {
            attackByOpponent(opponentPalmon, playerPalmon);
            if (!playerPalmon.isDefeated()) {
                sleep(2000); // Add a delay before the counterattack
                attack(playerPalmon, opponentPalmon);
            }
        }
    }

    /**
     * Executes an attack from the attacker Palmon to the defender Palmon.
     * The attacker Palmon chooses a move to use, and the defender Palmon receives the damage.
     * The attack can miss if the move's accuracy is lower than a random number between 0 and 100.
     * The attack can be not effective if the damage is lower than 0.
     * 
     * @param attacker the Palmon that attacks
     * @param defender the Palmon that receives the attack
     * Time Complexity: O(1)
     */
    private void attack(Palmon attacker, Palmon defender) {
        boolean waitForMove = true;
        int moveChoice = 0;

        System.out.println();
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_YOUR_MOVE", Player.getUserName()), "green"));

        checkForMaxUsages(attacker);
        while (waitForMove) {
            TableCreator.printPalmonFightMoves(attacker);
            moveChoice = InputManager.Integer("BATTLE_CHOOSE_MOVE", 0, attacker.getFightMoves().size());
            if (moveChoice == 0) {
                printBattleStatus(playerTeam, opponentTeam);
                InputManager.EnterToContinue();
            } else {
                waitForMove = false;
            }
        }

        Move chosenMove = attacker.getFightMoves().get(moveChoice - 1);
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_USED", attacker.getName(), chosenMove.getName()), "magenta"));
        sleep(2000); // Add a delay before showing the move effect

        if (chosenMove.getAccuracy() < Math.random() * 100) {
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_MISSED_ATTACK", attacker.getName()), "yellow"));
            return;
        }

        int damage = attacker.getAttack() + chosenMove.getDamage();
        float effectivity = DataStorageService.getEffectivityMultiplier(attacker.getPrimaryType(), defender.getPrimaryType());
        damage = (int) (damage * effectivity) - defender.getDefense();

        if (damage < 0) {
            damage = 0;
            System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_ATTACK_NOT_EFFECTIVE"), "yellow"));
            return;
        }

        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_DEALT", attacker.getName(), damage, defender.getName()), "green"));
        chosenMove.use();
        defender.attack(damage);
    }

    /**
     * Executes an attack from the bot Palmon to the player's Palmon.
     * The bot Palmon chooses a random move to use, and the player's Palmon receives the damage.
     * @param attacker the Palmon that attacks
     * @param defender the Palmon that receives the attack
     * Time Complexity: O(1)
     */
    private void attackByOpponent(Palmon attacker, Palmon defender) {
        System.out.println();
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_YOUR_MOVE", Player.getOpponentName()), "red"));
        checkForMaxUsages(attacker);
        int moveChoice = (int) (Math.random() * attacker.getFightMoves().size());
        Move chosenMove = attacker.getFightMoves().get(moveChoice);
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_USED", attacker.getName(), chosenMove.getName()), "magenta"));
        sleep(2000); // Add a delay before showing the move effect

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

    /**
     * Checks if a team is defeated.
     * A team is defeated if all of its Palmons are defeated.
     * @param team the team to check
     * @return true if the team is defeated, false otherwise
     * Time Complexity: O(n)
     */
    private boolean isTeamDefeated(Team team) {
        return team.getPalmons().stream().allMatch(Palmon::isDefeated);
    }

    /**
     * Removes the moves that have reached their maximum number of usages from the Palmon's fight moves.
     * @param palmon the Palmon to check
     * Time Complexity: O(n)
     */
    private void checkForMaxUsages(Palmon palmon) {
        palmon.getFightMoves().removeIf(move -> !move.isUsable());
    }

    /**
     * Prints the battle status, including the status of the player's team and the opponent's team.
     * The status includes the name, health, and status of each Palmon in the team.
     * Time Complexity: O(n)
     */
    public static void printBattleStatus(Team playerTeam, Team opponentTeam) {
        System.out.println();
        System.out.println(ConsoleColors.colorizeAndBold(LocaleManager.getMessage("BATTLE_STATUS"), "cyan"));
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_TEAM_STATUS", Player.getUserName()), "green"));
        TableCreator.printTeam(playerTeam);
        System.out.println(ConsoleColors.colorize(LocaleManager.getMessage("BATTLE_TEAM_STATUS", Player.getOpponentName()), "red"));
        TableCreator.printTeam(opponentTeam);
    }

    /**
     * Adds a delay to the execution of the program.
     * @param milliseconds the number of milliseconds to sleep
     * Time Complexity: O(1)
     */
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
