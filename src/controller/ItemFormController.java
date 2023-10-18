package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    public TextField txtItemCode;
    public TextField txtItemDescription;
    public TextField txtItemUnitPrice;
    public TextField txtItemQtyOnHand;
    public TableView tblItem;
    public TableColumn clmItemCode;
    public TableColumn clmItemDescription;
    public TableColumn clmItemQtyOnHand;
    public TableColumn clmItemQty;


    public void btnItemAddAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{
            Item item = new Item(
                    txtItemCode.getText(),
                    txtItemDescription.getText(),
                    Integer.parseInt(txtItemUnitPrice.getText()),
                    Integer.parseInt(txtItemQtyOnHand.getText()
                    ));
            boolean addedItem = new ItemController().addItem(item);
            if (addedItem){
                new Alert(Alert.AlertType.INFORMATION, "Added success !").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Added Failed !").show();
            }


        }catch (NumberFormatException ex){
            new Alert(Alert.AlertType.ERROR, "Added Failed").show();
        }

    }

    public void btnItemCancelAction(ActionEvent actionEvent) {
        clear();
    }
    public void clear(){
        txtItemCode.setText("");
        txtItemDescription.setText("");
        txtItemUnitPrice.setText("");
        txtItemQtyOnHand.setText("");
    }

    public void btnItemSearchAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL="Select * From Item where Code= '"+txtItemCode.getText()+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement=connection.createStatement();
        ResultSet rst=statement.executeQuery(SQL);
        if(rst.next()){
            Item item= new Item(txtItemCode.getText(),rst.getString(2),rst.getInt(3),rst.getInt(4));
            txtItemDescription.setText(item.getDescription());
            txtItemUnitPrice.setText(item.getUnitPrice()+"");
            txtItemQtyOnHand.setText(item.getQtyOnHand()+"");
        }else {
            new Alert(Alert.AlertType.ERROR, "Item not found !").show();
        }

    }

    public void btnItemUpdateAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL="Update Item set description=?,unitPrice=?,qtyOnHand=? where Code=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        preparedStatement.setObject(1,txtItemDescription.getText());
        preparedStatement.setObject(2,txtItemUnitPrice.getText());
        preparedStatement.setObject(3,txtItemQtyOnHand.getText());
        preparedStatement.setObject(4,txtItemCode.getText());

        if(preparedStatement.executeUpdate()>0){
            new Alert(Alert.AlertType.INFORMATION, "Update success !").show();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Update Failed !").show();
        }

    }

    public void btnItemDeleteAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL="Delete From Item where Code='"+txtItemCode.getText()+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(SQL);
        if(i>=0){
            new Alert(Alert.AlertType.INFORMATION, "Deleted success !").show();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Deleted Failed !").show();
        }
    }
    public void setTableValuesToTxt(Item newValue){
        txtItemCode.setText(newValue.getItemCode());
        txtItemDescription.setText(newValue.getDescription());
        txtItemUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtItemQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
    }
    public void loadTable(){
        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmItemQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmItemQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        String SQL ="Select * From Item";
        ObservableList<Item> list = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()){
                Item item=new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4));
                list.add(item);
            }
            tblItem.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable,
                                                                            oldValue,
                                                                            newValue) -> {
            if (newValue!=null) {
                setTableValuesToTxt((Item) newValue);
            }
        });
    }

    public void txtOnAcrion(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnItemSearchAction( actionEvent);
    }

    public void btnRefreshOnAction(ActionEvent actionEvent) {
        loadTable();
    }
}
