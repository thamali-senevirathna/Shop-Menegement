package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;


public class CustomerController1 implements CustomerService {


    @Override
    public boolean addCustomer(Customer customer) {
        String SQL = "Insert into Customer Values(?,?,?,?)";

        int i = -1;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, customer.getCustomerId());
            psTm.setObject(2, customer.getName());
            psTm.setObject(3, customer.getAddress());
            psTm.setObject(4, customer.getSalary());
            i = psTm.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        return i > 0 ? true : false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String SQL = "Update Customer set name=?, address=?, salary=? where id=?";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, customer.getName());
            psTm.setObject(2, customer.getAddress());
            psTm.setObject(3, customer.getSalary());
            psTm.setObject(4, customer.getCustomerId());
            int i = psTm.executeUpdate();
            return i > 0 ? true : false;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public Customer searchCustomer(String customerId) {
        String SQL = "Select * From Customer where id='" + customerId + "'";

        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(SQL);
            if (resultSet.next()) {
                Customer customer = new Customer(customerId, resultSet.getString("name"), resultSet.getString("address"), resultSet.getDouble("salary"));
                return customer;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        int i = 0;
        try {
            i = DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Customer where id='" + customerId + "'");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return i > 0 ? true : false;
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {
        String SQL = "Select * From Customer";
        ObservableList<Customer> list = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(SQL);

            while (rst.next()) {
                Customer customer = new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getDouble("salary"));
                list.add(customer);
            }
            return list;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<String> getCustomerId(){
        ArrayList<String> ids=new ArrayList<>();
        String SQL="SELECT * FROM Customer";
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement(SQL).executeQuery();
            while (resultSet.next()){
                ids.add(resultSet.getString(1));
            }
            return ids;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

