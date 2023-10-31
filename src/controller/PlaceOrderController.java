package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PlaceOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaceOrderController {
/*public boolean placeOrder(PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {
    PreparedStatement preparedStatement;
    try (Connection connection = DBConnection.getInstance().getConnection()) {
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO PlaceOrder value(?,?,?)");
        preparedStatement.setObject(1, placeOrder.getOrderId());
        preparedStatement.setObject(2, placeOrder.getDate());
        preparedStatement.setObject(3, placeOrder.getCustomerId());
        boolean orderIsAdd =preparedStatement.executeUpdate()>0;
        if (orderIsAdd){
          //  boolean orderDetailsAdded=
        }

    }

}*/




}
