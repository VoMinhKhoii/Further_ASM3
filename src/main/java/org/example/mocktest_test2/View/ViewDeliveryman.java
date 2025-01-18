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
import org.example.mocktest_test2.Model.Deliveryman;
import org.example.mocktest_test2.Repository.DeliverymanRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewDeliveryman implements Initializable {

    @FXML
    private Button createDeliveryButton;

    @FXML
    private TableColumn<Deliveryman, Integer> deliverymanIDColumn;

    @FXML
    private TableColumn<Deliveryman, String> deliveryNameColumn;

    @FXML
    private TableColumn<Deliveryman, String> deliverymanPhoneNumberColumn;

    @FXML
    private TableView<Deliveryman> deliverymanTable;

    @FXML
    private Button deleteDeliveryButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button updateDeliverymanButton;

    private ObservableList<Deliveryman> deliverymen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();

        returnButton.setOnAction(event -> createStage("/org/example/mocktest_test2/HomePage.fxml", returnButton));

        createDeliveryButton.setOnAction(event -> createStage("/org/example/mocktest_test2/CreateDeliveryman.fxml", createDeliveryButton));

        deleteDeliveryButton.setOnAction(event -> {
            Deliveryman selectedDeliveryman = deliverymanTable.getSelectionModel().getSelectedItem();
            if (selectedDeliveryman != null) {
                DeliverymanRepository.getInstance().delete(selectedDeliveryman.getID());
                loadDeliverymanData(); // Refresh table data
                showAlert("Deliveryman deleted successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("No deliveryman selected for deletion.", Alert.AlertType.WARNING);
            }
        });

        updateDeliverymanButton.setOnAction(event -> {
            Deliveryman selectedDeliveryman = deliverymanTable.getSelectionModel().getSelectedItem();
            if (selectedDeliveryman != null) {
                createStage("/org/example/mocktest_test2/UpdateDeliveryman.fxml", updateDeliverymanButton);
            } else {
                showAlert("No deliveryman selected for update.", Alert.AlertType.WARNING);
            }
        });
    }

    public void initializeTable() {
        loadDeliverymanData();

        deliverymanIDColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getID()).asObject()
        );
        deliveryNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        deliverymanPhoneNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPhoneNumber())
        );

        // Set placeholder for empty table
        deliverymanTable.setPlaceholder(new Label("No deliverymen available."));
    }


    private void loadDeliverymanData() {
        List<Deliveryman> deliverymanList = DeliverymanRepository.getInstance().findAll();

        if (deliverymanList == null || deliverymanList.isEmpty()) {
            showAlert("No deliverymen found.", Alert.AlertType.INFORMATION);
            return;
        }

        deliverymen = FXCollections.observableArrayList(deliverymanList);
        deliverymanTable.setItems(deliverymen);
    }

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

    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
