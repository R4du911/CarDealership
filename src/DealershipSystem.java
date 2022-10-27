import java.util.List;

public interface DealershipSystem {
    void remove(Merchandise merch);
    void add(Merchandise merch);
    void update(Merchandise merch);
    List<Part> getAllParts();
    List<Car> getAllCars();
}
