package controller;

import javafx.collections.ObservableList;
import model.Customer;

import java.sql.SQLException;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String customerId);
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    ObservableList<Customer> getAllCustomer();
}
