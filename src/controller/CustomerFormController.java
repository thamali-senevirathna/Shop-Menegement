package controller;

import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

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
       CustomerController customerController;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            customerController = new CustomerController();
            setCellValueFactory();
            loadTable();
            tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (null != newValue) {
                    setTableValuesToTxt(newValue);
                }
            });
        }

        public void btnAddAction(ActionEvent actionEvent) {
            String customerId = txtCustId.getText();
            String name = txtCustName.getText();
            String address = txtCustAddress.getText();
            double salary = Double.parseDouble(txtCustSalary.getText());
            Customer customer = new Customer(customerId, name, address, salary);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                boolean isAdd = customerController.addCustomer(customer);
                clear();
                if (isAdd) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Added !").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
                }
            }

        }

        public void btnUpdateAction(ActionEvent actionEvent) {
            String customerId = txtCustId.getText();
            String name = txtCustName.getText();
            String address = txtCustAddress.getText();
            double salary = Double.parseDouble(txtCustSalary.getText());
            Customer customer = new Customer(customerId, name, address, salary);
            boolean isUpdated = customerController.updateCustomer(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated !").show();
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }

        }

        public void btnSearchAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            Customer customer = customerController.searchCustomer(txtCustId.getText());
            System.out.println(customer);
            if (customer != null) {
                txtCustName.setText(customer.getName());
                txtCustAddress.setText(customer.getAddress());
                txtCustSalary.setText(String.valueOf(customer.getSalary()));
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found !").show();
            }
          clear();
        }

        public void btnDeleteAction(ActionEvent actionEvent) {
            String customerId = txtCustId.getText();
            boolean isDeleted;
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                isDeleted = customerController.deleteCustomer(customerId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Deleted !").show();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
                }
            }

        }


    public void btnCancelAction(ActionEvent actionEvent) {
        clear();
    }
    public void clear(){
        txtCustId.setText("");
        txtCustName.setText("");
        txtCustAddress.setText("");
        txtCustSalary.setText("");
    }


        private void loadTable() {
            ObservableList<Customer> allCustomer = customerController.getAllCustomer();
            tblCustomer.setItems(allCustomer);
        }

        public void setTableValuesToTxt(Object newValue) {
            Customer customer= (Customer) newValue;
            txtCustId.setText(customer.getCustomerId());
            txtCustName.setText(((Customer) newValue).getName());
            txtCustAddress.setText(((Customer) newValue).getAddress());
            txtCustSalary.setText(String.valueOf(((Customer) newValue).getSalary()));
        }

        private void setCellValueFactory() {
            clmCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            clmCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
            clmCustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            clmCustSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        }
    public void txtOnAcrion(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSearchAction( actionEvent);
    }
    public void btnRefreshOnAction(ActionEvent actionEvent) {
        loadTable();
    }

        public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
            ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
            List<String> ids = new ArrayList<>();
            while (rst.next()) {
                ids.add(
                        rst.getString(1)
                );

            }
            return ids;
        }


    }

