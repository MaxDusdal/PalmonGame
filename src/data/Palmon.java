package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import service.DataStorageService;
import utils.ConsoleHelpers.TableCreator;

public class Palmon {
    public final int id;
    public final String name;
    private final int height;
    private final int weight;
    public final String[] types = new String[2];
    int hp;
    private final int attack;
    private final int defense;
    public final int speed;
    private int level = 100;
    public static final int maxMoves = 4;
    private Map<Integer, Integer> moves = new HashMap<>();
    private ArrayList<Move> fightMoves = new ArrayList<>();

    public boolean isDefeated = false;

    public Palmon(int id, String name, int height, int weight, String type1, String type2,
                  int hp, int attack, int defense, int speed) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.types[0] = type1;
        this.types[1] = type2;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrimaryType() {
        return types[0];
    }

    public String getSecondaryType() {
        return types[1];
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getHp() {
        return hp;
    }

    public int setHp(int hp) {
        return this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    // Method to add a move
    public void addMove(int moveId, int learnedOnLevel) {
        moves.put(moveId, learnedOnLevel);
    }

    public void printMoves() {
        for (Map.Entry<Integer, Integer> move : moves.entrySet()) {
            Optional<Move> moveData = DataStorageService.getMoveById(move.getKey());
            if (moveData.isPresent()) {
                System.out.println("Move ID: " + move.getKey() + ", Name: " + moveData.get().getName()
                        + ", Learned On Level: " + move.getValue());
            }
        }
    }

    public int attack(int damage) {
        int newHP = hp - damage;
        if (newHP <= 0) {
            hp = 0;
            isDefeated = true;
        } else {
            hp = newHP;
        }
        return hp;
    }

    // Getters for the moves
    public Map<Integer, Integer> getMoves() {
        return moves;
    }

    public boolean isDefeated() {
        return hp <= 0;
    }

    // Display method to print Palmon details
    public void display() {
        TableCreator.printPalmon(this);
    }

    // Method to set the fight moves based on the highest damage
    public void setFightMoves() {
        fightMoves = moves.keySet().stream()
                .map(moveId -> {
                    Optional<Move> move = DataStorageService.getMoveById(moveId);
                    return move;
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted((m1, m2) -> Integer.compare(m2.getDamage(), m1.getDamage()))
                .limit(maxMoves)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Move> getFightMoves() {
        return fightMoves;
    }
}
