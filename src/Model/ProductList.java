package Model;

import java.util.ArrayList;
import java.util.List;

public class ProductList {

    private List<Merchandise> purchased;

    public ProductList() {
        this.purchased = new ArrayList<>();
    }

    public List<Merchandise> getPurchased() {
        return purchased;
    }

    public void setPurchased(List<Merchandise> purchased) {
        this.purchased = purchased;
    }

    public void addProductToList(Merchandise merch){
        this.purchased.add(merch);
    }

    public void removeProductFromList(Merchandise merch){
        this.purchased.remove(merch);
    }
}
