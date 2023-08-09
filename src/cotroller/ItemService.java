package cotroller;

import javafx.collections.ObservableList;
import model.Customer;
import model.Item;

public interface ItemService {
    boolean addItem(Item item);
    boolean updateItem(Item item);

    Item searchItem(String code);
    boolean deleteItem(String code);
    ObservableList<Item> getAllItem();
}
