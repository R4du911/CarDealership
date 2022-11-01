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

    public void setProductList(ProductList productList) {
        this.productList = productList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
