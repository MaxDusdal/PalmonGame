package data;

import java.io.Serializable;

public class Move implements Serializable {
    public final int id;

    final String name;
    final int damage;
    private int maxUsages;
    private int usages = 0;
    final int accuracy;
    private final String type;

    public Move(int id, String name, int damage, int maxUsages, int accuracy, String type) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.maxUsages = maxUsages;
        this.accuracy = accuracy;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getUsages() {
        return usages;
    }

    public int getMaxUsages() {
        return maxUsages;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getType() {
        return type;
    }

    public void use() {
        usages++;
    }

    public boolean isUsable() {
        return usages < maxUsages;
    }

    @Override
    public String toString() {
        return "Move{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", damage=" + damage +
                ", maxUsages=" + maxUsages +
                ", usages=" + usages +
                ", accuracy=" + accuracy +
                ", type='" + type + '\'' +
                '}';
    }
}
