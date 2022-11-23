package Interface;

import Model.Merchandise;

import java.util.Date;
import java.util.List;

public interface CustomerSystem {
    void addProductToList(int ID) throws Exception;
    void removeProductFromList(int ID) throws Exception;
    List<Merchandise> viewPendingOrder();
    void addOrder(Date date) throws Exception;
}
