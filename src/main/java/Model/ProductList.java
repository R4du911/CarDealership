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

    /**
     * @param merch to be added to a customer's shopping list
     * Adds a merch to a customer's shopping list
     */
    public void addProductToList(Merchandise merch) {
        this.purchased.add(merch);
    }

    /**
     * @param ID of the merch to be removed
     * Removes a merch from a customer's shopping list
     */
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
                System.out.println("\n");
            }
            count++;
            result += "\n";
        }
        return result;
    }
}
