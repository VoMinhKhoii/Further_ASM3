package org.example.mocktest_test2.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePage implements Initializable {

    @FXML
    private Button viewCustomerButton;

    @FXML
    private Button viewDeliverymanButton;

    @FXML
    private Button viewItemsButton;

    @FXML
    private Button viewOrdersButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewCustomerButton.setOnAction(event -> {
            createStage("/org/example/mocktest_test2/ViewCustomers.fxml", viewCustomerButton);
        });

        viewDeliverymanButton.setOnAction(event -> {
            createStage("/org/example/mocktest_test2/ViewDeliveryman.fxml", viewDeliverymanButton);
        });

        viewItemsButton.setOnAction(event -> {
            createStage("/org/example/mocktest_test2/ViewItems.fxml", viewItemsButton);
        });

        viewOrdersButton.setOnAction(event -> {
            createStage("/org/example/mocktest_test2/ViewOrder.fxml", viewOrdersButton);
        });
    }

    public void createStage(String filePath, Button button) {
        try {
            URL resource = getClass().getResource(filePath);
            if (resource == null) {
                System.out.println("FXML file not found: " + filePath);
            }
            FXMLLoader Loader = new FXMLLoader(getClass().getResource(filePath));
            Parent root = Loader.load();
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (Exception e) {
            //showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert( String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
