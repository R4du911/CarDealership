import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Person{
    private List<Order> orders;
    private Double money;
    final private Inventory inventory;

    public Customer(String user, String passwd, String firstName, String lastName, List<Order> orders, Double money, Inventory inventory) {
        super(user, passwd, firstName, lastName);
        this.orders = orders;
        this.money = money;
        this.inventory = inventory;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<Car>();

        for(Merchandise merch : this.inventory.getCarsAndParts()){
            if(merch instanceof Car){
                cars.add((Car) merch);
            }
        }

        return cars;
    }

    public List<Part> getAllParts() {
        List<Part> parts  = new ArrayList<Part>();
        for(Merchandise merch : this.inventory.getCarsAndParts()){
            if(merch instanceof Part){
                parts.add((Part) merch);
            }
        }
        return parts;
    }

    public void addOrder(ProductList products, Date date){
        Double sumPrice = 0.0;

        for(Merchandise merch : products.getPurchased()){
            if(!this.inventory.getCarsAndParts().contains(merch)){
                //throw exception not exist
                return;
            }else{
                sumPrice += merch.getPrice();
                if(sumPrice > this.getMoney()) {
                    //throw exception not enough money
                    return;
                }
            }
        }

        this.setMoney(this.getMoney() - sumPrice);
        this.orders.add(new Order(products,date));
    }

    @Override
    public boolean checkStatus() {
        return false;
    }
}
