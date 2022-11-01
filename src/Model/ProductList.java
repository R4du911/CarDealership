package Model;

import java.util.List;

public class ProductList {
    private List<Merchandise> purchased;

    public List<Merchandise> getPurchased() {
        return purchased;
    }

    public void setPurchased(List<Merchandise> purchased) {
        this.purchased = purchased;
    }
}
