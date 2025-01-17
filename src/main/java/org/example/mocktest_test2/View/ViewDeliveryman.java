package org.example.mocktest_test2.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewDeliveryman implements Initializable {
    @FXML
    private Button createDeliveryButton;

    @FXML
    private TableColumn<?, ?> customerOrderIDColumn;

    @FXML
    private Button deleteDeliveryButton;

    @FXML
    private TableColumn<?, ?> deliveryNameColumn;

    @FXML
    private TableColumn<?, ?> deliverymanIDColumn;

    @FXML
    private TableColumn<?, ?> deliverymanPhoneNumberColumn;

    @FXML
    private TableView<?> deliverymanTable;

    @FXML
    private Button returnButton;

    @FXML
    private Button updateDeliverymanButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnButton.setOnAction(event -> {
            createStage("/org/example/mocktest_test2/HomePage.fxml", returnButton);
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
            showAlert(e.getMessage());
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
