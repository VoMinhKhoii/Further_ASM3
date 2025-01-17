module org.example.mocktest_test2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.mocktest_test2 to javafx.fxml;
    exports org.example.mocktest_test2;
    exports org.example.mocktest_test2.View;
    opens org.example.mocktest_test2.View to javafx.fxml;

}