package Model.Repo;

import Model.Merchandise;

import java.util.ArrayList;
import java.util.List;

public class InMemoInventory {
    private final List<Merchandise> carsAndParts;

    public List<Merchandise> getCarsAndParts() {
        return carsAndParts;
    }

    public InMemoInventory() {
        this.carsAndParts = new ArrayList<>();
    }

    public void add_Merch(Merchandise merch) {
        this.carsAndParts.add(merch);
    }

    public void remove_Merch(int ID){
        for (Merchandise merch : this.getCarsAndParts()) {
            if (merch.getID() == ID) {
                this.carsAndParts.remove(merch);
                return;
            }
        }
    }
}
