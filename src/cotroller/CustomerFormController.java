package cotroller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable{

    public TextField txtCustId;

    public TextField txtCustName;
    public TextField txtCustAddress;
    public TextField txtCustSalary;
    public TableView tblCustomer;
    public TableColumn clmCustId;
    public TableColumn clmCustName;
    public TableColumn clmCustAddress;
    public TableColumn clmCustSalary;
    private List<String> customerId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     loadTable();
     clmCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
     clmCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
     clmCustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
     clmCustSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }
    public void btnAddAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{
            Customer customer = new Customer(
                    txtCustId.getText(),
                    txtCustName.getText(),
                    txtCustAddress.getText(),
                    Double.parseDouble(txtCustSalary.getText()
                    ));
            boolean addedCustomer = new CustomerController().addCustomer(customer);
            if (addedCustomer){
                new Alert(Alert.AlertType.INFORMATION, "Added success !").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Added Failed !").show();
            }


        }catch (NumberFormatException ex){
            new Alert(Alert.AlertType.ERROR, "Added Failed").show();
        }
    }


    public void btnCancelAction(ActionEvent actionEvent) {clear();}

    public void btnUpdateAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

    }
    public void clear(){
        txtCustId.setText("");
        txtCustName.setText("");
        txtCustAddress.setText("");
        txtCustSalary.setText("");
    }
    public void btnDeleteAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL="Delete From Customer where id='"+txtCustId.getText()+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(SQL);
        if(i>=0){
            new Alert(Alert.AlertType.INFORMATION, "Deleted success !").show();
            loadTable();
           clear();
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Deleted Failed !").show();
        }
    }

    public void btnSearchAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL="Select * From Customer where id= '"+txtCustId.getText()+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement=connection.createStatement();
        ResultSet rst=statement.executeQuery(SQL);
        if(rst.next()){
            Customer customer= new Customer(txtCustId.getText(),rst.getString("name"),rst.getString("address"),rst.getDouble("salary"));
            txtCustName.setText(customer.getName());
            txtCustAddress.setText(customer.getAddress());
            txtCustSalary.setText(customer.getSalary()+"");
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer not found !").show();
        }
    }
    public void txtOnAcrion(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSearchAction( actionEvent);
    }
    public void setTableValuesToTxt(Customer newValue){
        txtCustId.setText(newValue.getCustomerId());
        txtCustName.setText(newValue.getName());
        txtCustAddress.setText(newValue.getAddress());
        txtCustSalary.setText(String.valueOf(newValue.getSalary()));
    }


    public void loadTable(){
        ObservableList<Customer> allCustomer = new CustomerController().getAllCustomer();
        tblCustomer.setItems(allCustomer);
    }


    public void btnRefreshOnAction(ActionEvent actionEvent) {
        loadTable();
    }
}
