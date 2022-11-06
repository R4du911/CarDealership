package Model.Repo;

import Model.Merchandise;

import java.util.ArrayList;
import java.util.List;

public class InMemoInventory {
    private List<Merchandise> carsAndParts;

    public List<Merchandise> getCarsAndParts() {
        return carsAndParts;
    }

    public InMemoInventory() {
        this.carsAndParts = new ArrayList<>();
    }

    public void setCarsAndParts(List<Merchandise> carsAndParts) {
        this.carsAndParts = carsAndParts;
    }

    public void add_Merch(Merchandise merch) {
        this.carsAndParts.add(merch);
    }

    public void remove_Merch(int ID) throws IllegalArgumentException {
        for (Merchandise merch : this.getCarsAndParts()) {
            if (merch.getID() == ID) {
                this.carsAndParts.remove(merch);
                return;
            }
        }

        throw new IllegalArgumentException("Product does not exist");
    }
}
