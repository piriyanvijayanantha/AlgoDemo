module org.example.binarysearch {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.binarysearch to javafx.fxml;
    exports org.example.binarysearch;
}