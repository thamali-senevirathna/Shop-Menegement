package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;


public class CustomerController implements CustomerService {


    @Override
    public boolean addCustomer(Customer customer) {
        int i = -1;
        try {
          i = CrudUtil.execute("Insert into Customer Values(?,?,?,?)",
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            );
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return i > 0;
    }
    @Override
    public boolean updateCustomer(Customer customer) {
        int i = -1;
        try {
            i = CrudUtil.execute("Update Customer set name=?, address=?, salary=? where id=?",
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary(),
                    customer.getCustomerId()
            );
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return i>0;
    }

    @Override
    public Customer searchCustomer(String customerId) {
        try {
             ResultSet rst= CrudUtil.execute("select * From Customer where id= ?",customerId);
            rst.next();
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3), rst.getDouble(4));
        } catch (ClassNotFoundException | SQLException e) {
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
        return i > 0;
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

