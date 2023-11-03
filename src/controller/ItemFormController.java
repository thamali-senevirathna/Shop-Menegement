package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    public TextField txtItemCode;
    public TextField txtItemDescription;
    public TextField txtItemUnitPrice;
    public TextField txtItemQty;
    public TextField txtItemStock;
    public TableView tblItem;
    public TableColumn clmItemCode;
    public TableColumn clmItemDescription;
    public TableColumn clmItemQty;
    public TableColumn clmItemStock;
    public TableColumn clmItemUnitPrice;
    ItemController itemController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemController = new ItemController();
        setCellValueFactory();
        loadTable();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setTableValuesToTxt(newValue);
            }
        });
    }
    public void btnItemAddAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        String description = txtItemDescription.getText();
        double unitPrice =Double.parseDouble(txtItemUnitPrice.getText());
        int stock = Integer.parseInt(txtItemStock.getText());
        int qty=Integer.parseInt(txtItemQty.getText());
        Item item = new Item(itemCode, description, unitPrice, stock,qty);

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this item ?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isAdd = itemController.addItem(item);
            clear();
            if (isAdd) {
                new Alert(Alert.AlertType.INFORMATION, "Item Added !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }
        }

    }
    public void btnItemUpdateAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        String description = txtItemDescription.getText();
        double unitPrice =Double.parseDouble(txtItemUnitPrice.getText());
        int stock = Integer.parseInt(txtItemStock.getText());
        int qty=Integer.parseInt(txtItemQty.getText());
        Item item = new Item(itemCode, description, unitPrice, stock,qty);
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update this item?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isUpdated = itemController.updateItem(item);
            System.out.println(item);
          clear();
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }
        }

    }
    public void btnItemSearchAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item item = itemController.searchItem(txtItemCode.getText());
       System.out.println(item);
        if (item != null) {
            txtItemDescription.setText(item.getDescription());
            txtItemUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtItemStock.setText(String.valueOf(item.getStock()));
            txtItemQty.setText(String.valueOf(item.getQty()));
        }else {
            new Alert(Alert.AlertType.ERROR, "Item Not Found !").show();
        }

    }
    public void btnItemDeleteAction(ActionEvent actionEvent) {
        String itemCodes = txtItemCode.getText();
        boolean isDeleted;
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            isDeleted = itemController.deleteItem(itemCodes);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted !").show();
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }
        }

    }
    public void btnItemCancelAction(ActionEvent actionEvent) {
        clear();
    }
    public void clear(){
        txtItemCode.setText("");
        txtItemDescription.setText("");
        txtItemUnitPrice.setText("");
        txtItemStock.setText("");
        txtItemQty.setText("");
    }


    private void loadTable() {
        ObservableList<Item> allItem = itemController.getAllItem();
        tblItem.setItems(allItem);
    }

    public void setTableValuesToTxt(Object newValue) {
        Item item= (Item) newValue;
        txtItemCode.setText(item.getItemCode());
        txtItemDescription.setText(((Item) newValue).getDescription());
        txtItemUnitPrice.setText(String.valueOf(((Item) newValue).getUnitPrice()));
        txtItemStock.setText(String.valueOf(((Item) newValue).getStock()));
        txtItemQty.setText(String.valueOf(((Item) newValue).getQty()));
    }

    private void setCellValueFactory() {
        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmItemStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        clmItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }
    public void txtOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnItemSearchAction( actionEvent);
    }
    public void btnRefreshOnAction(ActionEvent actionEvent) {
        loadTable();
    }

    public List<String> getItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> codes = new ArrayList<>();
        while (rst.next()) {
            codes.add(
                    rst.getString(1)
            );

        }
        return codes;
    }
}
