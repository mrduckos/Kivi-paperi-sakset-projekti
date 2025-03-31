module org.example.projektikps {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.projektikps to javafx.fxml;
    exports org.example.projektikps;
}