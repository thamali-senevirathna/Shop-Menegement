package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public BorderPane root;
    public Button btnLogOut;
    public Button btnOrder;
    private Stage stage;
    private Scene scene;
    private Parent parent;

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        parent= FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setY(0);
        stage.setX(10);
    }
    public void btnCustomerAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Customer.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }
    public void btnItemAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Item.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }
    public void btnOrderAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/PlaceOrder.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }
    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        parent= FXMLLoader.load(getClass().getResource("/view/WelCome.fxml"));
        stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
