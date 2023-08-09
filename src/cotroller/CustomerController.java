package cotroller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CustomerController implements CustomerService{
    @Override
    public boolean addCustomer(Customer customer) {
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this customer?", ButtonType.YES, ButtonType.NO).showAndWait();

            if (buttonType.get() == ButtonType.YES) {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
                pstm.setObject(1, customer.getCustomerId());
                pstm.setObject(2, customer.getName());
                pstm.setObject(3, customer.getAddress());
                pstm.setObject(4, customer.getSalary());

                if (pstm.executeUpdate() > 0) {
                   // new Alert(Alert.AlertType.INFORMATION, "Customer Added !").show();
                    return true;
                }


            }
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer>customerObservableList= FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * From Customer");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4));
                customerObservableList.add(customer);
            }
            return customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getCustomerIds(){
        ArrayList<String> ids = new ArrayList<>();
        String SQL = "SELECT * FROM Customer";
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            ResultSet rst = stm.executeQuery();
            while(rst.next()){
                ids.add(rst.getString(1));

            }
            return ids;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
