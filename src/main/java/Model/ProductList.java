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

    public void addProductToList(Merchandise merch){this.purchased.add(merch);}

    public void removeProductFromList(int ID){
        for (Merchandise merch : this.getPurchased()) {
            if (merch.getID() == ID) {
                this.purchased.remove(merch);
                return;
            }
        }
    }

    public String toStringProductList(){
        String result = "This order has the following products: " + "\n";
        int count = 1;
        for(Merchandise product : this.purchased){
            result += "Product " + count + ": ";
            if(product instanceof Car){
                result += ((Car) product).toStringCar();
            }
            else{
                result += ((Part) product).toStringPart();
            }
        }
        return result;
    }
}
