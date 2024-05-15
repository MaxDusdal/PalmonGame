package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    // Method to add a move
    public void addMove(int moveId, int learnedOnLevel) {
        moves.put(moveId, learnedOnLevel);
    }

    // Getters for the moves
    public Map<Integer, Integer> getMoves() {
        return moves;
    }

}

