package cotroller;

import javafx.collections.ObservableList;
import model.Customer;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);

    Customer searchCustomer(String id);
    boolean deleteCustomer(String id);
    ObservableList<Customer> getAllCustomer();
}
