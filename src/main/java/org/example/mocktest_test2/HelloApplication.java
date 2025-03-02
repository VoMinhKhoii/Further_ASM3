package org.example.mocktest_test2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Online orders management system");
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}