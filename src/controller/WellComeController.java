package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WellComeController{

    public BorderPane root;
    public TextField txtPassword;
    private String password="1234";
    private Stage stage;
    private Scene scene;
    private Parent parent;

    public void btnLogAction(ActionEvent actionEvent) throws IOException {
        if (txtPassword.getText().equals(password)){
            parent =FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
            stage.setX(200);
            stage.setY(0);
            emptyTxtPs();
        }
        else{
            new Alert(Alert.AlertType.ERROR,"Wrong Password !").show();
            emptyTxtPs();
        }

    }
    public void emptyTxtPs(){
        txtPassword.setText("");
    }

    public void txtPsOnAction(ActionEvent actionEvent) throws IOException {
        btnLogAction( actionEvent);
    }


}





