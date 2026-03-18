module com.qualibits.jeval{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.logging;

    opens com.qualibits.jeval to javafx.graphics, javafx.fxml;
    exports com.qualibits.jeval;
}