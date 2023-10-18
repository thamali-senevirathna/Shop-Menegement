//package controller;
//
//import db.DBConnection;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import model.PlaceOrder;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class PlaceOrderController {
//    String SQL = "Select * From Orders";
//    ObservableList<PlaceOrder> list = FXCollections.observableArrayList();
//    Connection connection;
//
//    {
//        try {
//            connection = DBConnection.getInstance().getConnection();
//            PreparedStatement pstm = connection.prepareStatement(SQL);
//            ResultSet rst = pstm.executeQuery();
//            while (rst.next()) {
//                PlaceOrder placeOrder = new PlaceOrder(rst.getString("id"), rst.getString("date"), rst.getString("customerId"));
//                list.add(placeOrder);
//            }
//            return list;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//
//}
