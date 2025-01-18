package org.example.mocktest_test2.View;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.mocktest_test2.Model.Customer;
import org.example.mocktest_test2.Repository.CustomerRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomer implements Initializable {

    @FXML
    private Button createCustomerButton;

    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerOrderIDColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberColumn;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button updateCustomerButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();

        // Button actions
        returnButton.setOnAction(event -> createStage("/org/example/mocktest_test2/HomePage.fxml", returnButton));
        createCustomerButton.setOnAction(event -> createStage("/org/example/mocktest_test2/CreateCustomer.fxml", createCustomerButton));
//        deleteCustomerButton.setOnAction(event -> handleDeleteCustomer());
//        updateCustomerButton.setOnAction(event -> handleUpdateCustomer());
    }

    /**
     * Initializes the customer table and loads data.
     */
    public void initializeTable() {
        loadCustomerData();
        customerIDColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getID()).asObject()
        );
        customerNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        customerPhoneNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPhoneNumber())
        );
        customerAddressColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress())
        );

        // Set placeholder for empty table
        customerTable.setPlaceholder(new Label("No customers available."));

        // Load data into the table

    }


    /**
     * Loads customer data from the repository and updates the table.
     */
    private void loadCustomerData() {
        List<Customer> customerList = CustomerRepository.getInstance().findAll();

        if (customerList == null || customerList.isEmpty()) {
            System.out.println("No customers found in repository.");
            customerTable.setPlaceholder(new Label("No customers available."));
            return;
        }

        System.out.println("Loaded customers: " + customerList);
        ObservableList<Customer> customers = FXCollections.observableArrayList(customerList);
        System.out.println("ObservableList: " + customers);
        customerTable.setItems(customers);
        System.out.println("Table updated with customer data.");
    }

    /**
     * Handles the delete customer button action.
     */
//    private void handleDeleteCustomer() {
//        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
//        if (selectedCustomer != null) {
//            CustomerRepository.getInstance().delete(selectedCustomer.getID());
//            loadCustomerData(); // Refresh the table
//            showAlert("Customer deleted successfully.", Alert.AlertType.INFORMATION);
//        } else {
//            showAlert("Please select a customer to delete.", Alert.AlertType.WARNING);
//        }
//    }

    /**
     * Handles the update customer button action.
     */
//    private void handleUpdateCustomer() {
//        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
//        if (selectedCustomer != null) {
//            // Pass customer data to the update screen
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mocktest_test2/UpdateCustomer.fxml"));
//                Parent root = loader.load();
//
//                // Get the controller for the update screen and pass the selected customer
//                UpdateCustomerController controller = loader.getController();
//                controller.setCustomer(selectedCustomer);
//
//                Stage currentStage = (Stage) updateCustomerButton.getScene().getWindow();
//                currentStage.setScene(new Scene(root));
//            } catch (Exception e) {
//                showAlert("Error loading update screen: " + e.getMessage(), Alert.AlertType.ERROR);
//            }
//        } else {
//            showAlert("Please select a customer to update.", Alert.AlertType.WARNING);
//        }
//    }

    /**
     * Opens a new stage based on the provided FXML file path.
     */
    public void createStage(String filePath, Button button) {
        try {
            URL resource = getClass().getResource(filePath);
            if (resource == null) {
                showAlert("FXML file not found: " + filePath, Alert.AlertType.ERROR);
                return;
            }
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (Exception e) {
            showAlert("Error loading the screen: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Displays an alert message to the user.
     */
    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
