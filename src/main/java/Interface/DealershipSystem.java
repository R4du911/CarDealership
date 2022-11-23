package Interface;

import Model.Car;
import Model.Merchandise;
import Model.Part;

import java.util.List;

public interface DealershipSystem {
    void remove(int ID) throws Exception;
    void add(Merchandise merch) throws Exception;
    void update(Merchandise merch) throws Exception;
    List<Part> getAllParts();
    List<Car> getAllCars();
}
