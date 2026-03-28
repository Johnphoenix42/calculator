module com.qualibits.qualeval{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.logging;

    opens com.qualibits.qualeval to javafx.graphics, javafx.fxml;
    exports com.qualibits.qualeval;
}