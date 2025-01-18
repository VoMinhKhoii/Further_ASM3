package org.example.mocktest_test2.View;

import javafx.beans.property.SimpleDoubleProperty;
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
import org.example.mocktest_test2.Model.Item;
import org.example.mocktest_test2.Repository.ItemRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewItem implements Initializable {

    @FXML
    private TableColumn<Item, Integer> itemIDColumn;

    @FXML
    private TableColumn<Item, String> itemNameColumn;

    @FXML
    private TableColumn<Item, Double> itemPriceColumn;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private Button createItemButton;

    @FXML
    private Button deleteItemButton;

    @FXML
    private Button updateItemButton;

    @FXML
    private Button returnButton;

    private ObservableList<Item> items;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();

        returnButton.setOnAction(event -> createStage("/org/example/mocktest_test2/HomePage.fxml", returnButton));

        createItemButton.setOnAction(event -> createStage("/org/example/mocktest_test2/CreateItem.fxml", createItemButton));

        deleteItemButton.setOnAction(event -> {
            Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                ItemRepository.getInstance().delete(selectedItem.getID());
                loadItemData(); // Refresh table data
                showAlert("Item deleted successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("No item selected for deletion.", Alert.AlertType.WARNING);
            }
        });

        updateItemButton.setOnAction(event -> {
            Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                createStage("/org/example/mocktest_test2/UpdateItem.fxml", updateItemButton);
            } else {
                showAlert("No item selected for update.", Alert.AlertType.WARNING);
            }
        });
    }

    public void initializeTable() {
        loadItemData();

        itemIDColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getID()).asObject()
        );
        itemNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        itemPriceColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject()
        );

        // Set placeholder for empty table
        itemTable.setPlaceholder(new Label("No items available."));
    }


    private void loadItemData() {
        List<Item> itemList = ItemRepository.getInstance().findAll();

        if (itemList == null || itemList.isEmpty()) {
            showAlert("No items found.", Alert.AlertType.INFORMATION);
            return;
        }

        items = FXCollections.observableArrayList(itemList);
        itemTable.setItems(items);
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
