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
}
