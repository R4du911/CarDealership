package Model;

public abstract class Merchandise {
    private String brand;
    private String model;
    private Double price;
    private int ID;

    public Merchandise(String brand, String model, Double price, int ID) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.ID = ID;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

