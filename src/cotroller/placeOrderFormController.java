package cotroller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class placeOrderFormController implements Initializable {


    public TextField txtOrderId;
    public TextField txtCustomerName;
    public ComboBox cmbCustomerId;
    public ComboBox cmbItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtOtyOnHand;
    public TextField txtQty;
    public Button btnAddTbl;
    public Button btnRemoveTbl;
    public TableView tblPlaceOrderChart;
    public TableColumn clmItemCode;
    public TableColumn clmDescription;
    public TableColumn clmUnitPrice;
    public TableColumn clmQty;
    public TableColumn clmTotal;
    public TextField txtDate;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    loadCustomerIDS();
    }
    void loadCustomerIDS() {
        ArrayList<String> customerIds = new CustomerController().getCustomerIds();
        cmbCustomerId.getItems().addAll(customerIds);

    }
}
