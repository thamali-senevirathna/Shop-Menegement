package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import model.Item;

import java.sql.*;
import java.util.ArrayList;

public class ItemController implements ItemService{
    @Override
    public boolean addItem(Item item) {
        String SQL = "Update Item set description=?, unitPrice=?, qtyOnHand=? where code=?";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, item.getItemCode());
            psTm.setObject(2, item.getDescription());
            psTm.setObject(3, item.getUnitPrice());
            psTm.setObject(4, item.getQtyOnHand());
            int i = psTm.executeUpdate();
            return i > 0 ? true : false;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean updateItem(Item item) {
        String SQL = "Update Item set description=?, unitPrice=?, qtyOnHand=? where code=?";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, item.getDescription());
            psTm.setObject(2, item.getUnitPrice());
            psTm.setObject(3, item.getQtyOnHand());
            psTm.setObject(4, item.getItemCode());
            int i = psTm.executeUpdate();
            return i > 0 ? true : false;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public Item searchItem(String code) {
        String SQL = "Select * From Item where code='" + code + "'";

        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(SQL);
            if (resultSet.next()) {
                Item item = new Item(code, resultSet.getString("description"), resultSet.getDouble("unitPrice"), resultSet.getInt("qtyOnHand"));
                return item;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteItem(String code) {
        int i = 0;
        try {
            i = DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Item where code='" + code + "'");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return i > 0 ? true : false;
    }

    @Override
    public ObservableList<Item> getAllItem() {
        String SQL = "Select * From Item";
        ObservableList<Item> list = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(SQL);

            while (rst.next()) {
                Item item = new Item(rst.getString("code"), rst.getString("description"), rst.getInt("unitPrice"), rst.getInt("qtyOnHand"));
                list.add(item);
            }
            return list;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
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
