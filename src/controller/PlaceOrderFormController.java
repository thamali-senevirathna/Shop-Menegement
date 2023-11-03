package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.CustomerBuyItem;
import model.Item;
import model.OrderDetails;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public TextField txtCustomerName;
    public ComboBox cmbCustomerId;
    public ComboBox cmbItemCode;

    public TableView tblPlaceOrderChart;
    public TableColumn clmItemCode;
    public TableColumn clmDescription;
    public TableColumn clmUnitPrice;
    public TableColumn clmQtyOnHand;
    public TableColumn clmTotal;
    public Label lblDate;
    public TextField txtAddress;
    public TextField lblOrderId;
    public TextField txtSalary;
    public TextField txtItemDescription;
    public TextField txtItemUnitPrice;
    public TextField txtItemQtyOnHand;
    public TextField txtCustomerBuyItemQty;
    public Label txtTotal;
    CustomerController customerController;
    ItemController itemController;
    Date date;
    public ObservableList<CustomerBuyItem>cartList= FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerController = new CustomerController();
        itemController = new ItemController();
        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        loadCustomerIDS();
        loadItemCodes();
        loadDate();
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData((String) newValue);
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            setItemData((String) newValue);
        });
    }

    void loadCustomerIDS() {
        ArrayList<String> customerIds = new CustomerController().getCustomerId();
        cmbCustomerId.getItems().addAll(customerIds);

    }

    void loadItemCodes() {
        ArrayList<String> itemCodes = new ItemController().getItemCode();
        cmbItemCode.getItems().addAll(itemCodes);
    }

    void loadDate() {
        date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    public void setCustomerData(String id) {
        Customer customer = customerController.searchCustomer(id);
        if (customer != null) {
            txtCustomerName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
            return ;
        }
        new Alert(Alert.AlertType.ERROR, "Customer Is not found !").show();
    }
    public void setItemData(String code) {
        Item item = itemController.searchItem(code);
        if (item!=null){
            txtItemDescription.setText(item.getDescription());
            txtItemUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtItemQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        }else {
            new Alert(Alert.AlertType.ERROR,"Item is not found !").show();
        }


    }
    public int isAlreadyExists(CustomerBuyItem customerBuyItem){
        for (int i=0; i<cartList.size();i++){
            String tempCode=String.valueOf(cartList.get(i).getItemCode());
            if (tempCode.equals(customerBuyItem.getItemCode())){
                return i;
            }

        }
        return -1;
    }

    public void btnAdd(ActionEvent actionEvent) {
        try{
            String itemCode = (String) cmbItemCode.getSelectionModel().getSelectedItem();
            String description = txtItemDescription.getText();
            int qty = Integer.parseInt(txtCustomerBuyItemQty.getText());
            double unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
            double total = unitPrice*qty;

            CustomerBuyItem customerBuyItem = new CustomerBuyItem(itemCode, description, unitPrice, qty, total);
            int row = isAlreadyExists(customerBuyItem);
            if (row==-1){
                cartList.add(customerBuyItem);
                tblPlaceOrderChart.setItems(cartList);
            }else {
                CustomerBuyItem cart =cartList.get(row);
                CustomerBuyItem tempCartTm = new CustomerBuyItem(customerBuyItem.getItemCode(),customerBuyItem.getDescription(),customerBuyItem.getUnitPrice(),customerBuyItem.getQty()+qty,total+customerBuyItem.getTotal());
                cartList.remove(row);
                cartList.add(tempCartTm);
            }
            tblPlaceOrderChart.setItems(cartList);
            calculateTotal();
        }catch (NumberFormatException ex){
            new Alert(Alert.AlertType.ERROR,"Added Failed !").show();
        }

    }
    public void calculateTotal(){
        double total=0;
        for (CustomerBuyItem customerBuyItem:cartList){
            total+=customerBuyItem.getTotal();
        }
        txtTotal.setText(String.valueOf(total));
    }

    public void btnRemove(ActionEvent actionEvent) {
//        int row = tblPlaceOrderChart.getSelectionModel().getSelectedIndex();
//        cartList.remove(row);
        txtItemDescription.setText("");
        txtItemUnitPrice.setText("");
        txtItemQtyOnHand.setText("");
        txtCustomerBuyItemQty.setText("");
//        calculateTotal();

    }



    public void btnPlaceOrder(ActionEvent actionEvent) {
        String orderId=lblOrderId.getText();
        String customerId = (String) cmbCustomerId.getValue();
        String orderDate=lblDate.getText();

        ArrayList<OrderDetails>orderDetailsArrayList=new ArrayList<>();

    }

   private void setOrderId() {
        try {
            String lastOrderId = getLastOrderId();
            if (lastOrderId != null) {
                lastOrderId = lastOrderId.split("[A-Z]")[1]; // D001==> 001
                System.out.println(lastOrderId);
                lastOrderId = String.format("D%03d", (Integer.parseInt(lastOrderId) + 1));
                lblOrderId.setText(lastOrderId);
            } else {
                lblOrderId.setText("D001");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getLastOrderId() throws SQLException, ClassNotFoundException, SQLException {
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        return rst.next() ? rst.getString("id") : null;
    }


    }
