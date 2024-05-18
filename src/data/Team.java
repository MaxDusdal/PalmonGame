package data;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Palmon> palmons;

    public Team() {
        this.palmons = new ArrayList<>();
    }

    public Team(List<Palmon> palmons) {
        this.palmons = new ArrayList<>(palmons);
    }

    public List<Palmon> getPalmons() {
        return palmons;
    }

    public void addPalmon(Palmon palmon) {
        this.palmons.add(palmon);
    }

    @Override
    public String toString() {
        return "Team{" +
                "palmons=" + palmons +
                '}';
    }
}
