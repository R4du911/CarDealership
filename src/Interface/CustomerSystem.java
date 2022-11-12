package Interface;

import Model.Merchandise;

import java.util.Date;
import java.util.List;

public interface CustomerSystem {
    void addProductToList(int ID);
    void removeProductFromList(int ID);
    List<Merchandise> viewPendingOrder();
    void addOrder(Date date);
}
