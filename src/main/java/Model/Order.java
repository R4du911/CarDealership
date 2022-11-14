package Model;

import java.util.Date;


public class Order {
    private ProductList productList;
    private Date date;

    public Order(ProductList productList, Date date) {
        this.productList = productList;
        this.date = date;
    }

    public ProductList getProductList() {
        return productList;
    }

    public String toStringOrder(){
        return "Date that the order was placed: " + this.date + "\n" + this.productList.toStringProductList();
    }
}
