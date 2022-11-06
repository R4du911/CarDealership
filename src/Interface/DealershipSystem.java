package Interface;

import Model.Car;
import Model.Merchandise;
import Model.Part;

import java.util.List;

public interface DealershipSystem {
    void remove(int ID);
    void add(Merchandise merch);
    void update(Merchandise merch);
    List<Part> getAllParts();
    List<Car> getAllCars();
}
