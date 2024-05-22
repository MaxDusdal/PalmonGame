package data;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Palmon> palmons;

    public Team() {
        this.palmons = new ArrayList<>();
    }

    /**
     * Adds a Palmon to the team.
     * 
     * @param palmon the Palmon to add
     * Time Complexity: O(1)
     * Reasoning: Adding an element to an ArrayList is an O(1) operation, assuming the list does not need to resize.
     */
    public void addPalmon(Palmon palmon) {
        this.palmons.add(palmon);
    }

    /**
     * Gets the list of Palmoms in the team.
     * 
     * @return the list of Palmoms
     * Time Complexity: O(1)
     * Reasoning: Retrieving a reference to the list is an O(1) operation.
     */
    public List<Palmon> getPalmons() {
        return palmons;
    }

    /**
     * Gets the list of remaining (non-defeated) Palmoms in the team.
     * 
     * @return the list of remaining Palmoms
     * Time Complexity: O(n) where n is the number of Palmoms in the team
     * Reasoning: The method iterates through the list of Palmoms to check if each one is defeated.
     */
    public List<Palmon> getRemainingPalmons() {
        List<Palmon> remainingPalmons = new ArrayList<>();
        for (Palmon palmon : palmons) {
            if (!palmon.isDefeated()) {
                remainingPalmons.add(palmon);
            }
        }
        return remainingPalmons;
    }

    /**
     * Checks if the team is defeated (all Palmoms are defeated).
     * 
     * @return true if the team is defeated, false otherwise
     * Time Complexity: O(n) where n is the number of Palmoms in the team
     * Reasoning: The method iterates through the list of Palmoms to check if each one is defeated.
     */
    public boolean isDefeated() {
        for (Palmon palmon : palmons) {
            if (!palmon.isDefeated()) {
                return false;
            }
        }
        return true;
    }
}
