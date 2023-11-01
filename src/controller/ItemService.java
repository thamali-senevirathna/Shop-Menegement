package controller;

import javafx.collections.ObservableList;
import model.Item;

import java.sql.SQLException;

public interface ItemService {
    boolean addItem(Item item) throws SQLException, ClassNotFoundException;
    boolean updateItem(Item item);

    Item searchItem(String code);
    boolean deleteItem(String code);
    ObservableList<Item> getAllItem();
}
