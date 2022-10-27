import java.util.List;

public class Customer extends Person{
    private List<Order> orders;
    private Double money;

    public Customer(String user, String passwd, String firstName, String lastName, List<Order> orders, Double money) {
        super(user, passwd, firstName, lastName);
        this.orders = orders;
        this.money = money;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public boolean checkStatus() {
        return false;
    }
}
