package Model.Repo;

import Model.Merchandise;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Merchandise> carsAndParts;

    public List<Merchandise> getCarsAndParts() {
        return carsAndParts;
    }

    public Inventory() {
        this.carsAndParts = new ArrayList<>();
    }

    /**
     * @param merch - Merch to be added
     * Adds a new merch to the merch-repository
     */
    public void add_Merch(Merchandise merch) {
        this.carsAndParts.add(merch);
    }

    /**
     * @param ID - ID of merch to be removed
     * Removes a merch from the merch-repository
     */
    public void remove_Merch(int ID){
        for (Merchandise merch : this.getCarsAndParts()) {
            if (merch.getID() == ID) {
                this.carsAndParts.remove(merch);
                return;
            }
        }
    }
}
