package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import model.Item;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class ItemController implements ItemService{
    @Override
    public boolean addItem(Item item){
        try {
            return CrudUtil.execute("Insert into Item Values(?,?,?,?)",
                    item.getItemCode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }




    @Override
    public boolean updateItem(Item item) {
        try {
            return CrudUtil.execute("Update Item set description=?, unitPrice=?, qtyOnHand=? where code=?",
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getItemCode()
                    );
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public Item searchItem(String code) {
        try {
            ResultSet rst= CrudUtil.execute("select * From Item where code= ?",code);
            rst.next();
            return new Item(rst.getString(1),rst.getString(2),rst.getDouble(3), rst.getInt(4));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObservableList<Item> getAllItem() {
        String SQL = "Select * From Item";
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(SQL);

            while (rst.next()) {
                Item item = new Item(rst.getString("code"), rst.getString("description"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand"));
                itemList.add(item);
            }
            return itemList;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteItem(String code) {
        try {
            return CrudUtil.execute("Delete From Item where code= ?",code);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }
    public ArrayList<String> getItemCode(){
        ArrayList<String> codes=new ArrayList<>();
        String SQL="SELECT * FROM Item";
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement(SQL).executeQuery();
            while (resultSet.next()){
                codes.add(resultSet.getString(1));
            }
            return codes;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
