import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Merchandise> carsAndParts;

    public List<Merchandise> getCarsAndParts() {
        return carsAndParts;
    }

    public Inventory() {
        this.carsAndParts = new ArrayList<Merchandise>();
    }

    public void setCarsAndParts(List<Merchandise> carsAndParts) {
        this.carsAndParts = carsAndParts;
    }

    public void add_Merch(Merchandise merch) {
        this.carsAndParts.add(merch);
    }

    public void remove_Merch(Merchandise merch) {
        this.carsAndParts.remove(merch);
    }

}
