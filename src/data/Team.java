package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Palmon> palmons;

    public Team() {
        this.palmons = new ArrayList<>();
    }

    public void addPalmon(Palmon palmon) {
        this.palmons.add(palmon);
    }

    public List<Palmon> getPalmons() {
        return palmons;
    }

    public List<Palmon> getRemainingPalmons() {
        List<Palmon> remainingPalmons = new ArrayList<>();
        for (Palmon palmon : palmons) {
            if (!palmon.isDefeated()) {
                remainingPalmons.add(palmon);
            }
        }
        return remainingPalmons;
    }

    public boolean isDefeated() {
        for (Palmon palmon : palmons) {
            if (!palmon.isDefeated()) {
                return false;
            }
        }
        return true;
    }
}
