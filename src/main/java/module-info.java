module com.qualibits.qualeval{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.logging;

    opens com.qualibits.qualeval to javafx.graphics, javafx.fxml;
    exports com.qualibits.qualeval;
    exports com.qualibits.qualeval.term.operator;
    exports com.qualibits.qualeval.term;
    opens com.qualibits.qualeval.term to javafx.fxml, javafx.graphics;
    exports com.qualibits.qualeval.buttons;
    opens com.qualibits.qualeval.buttons to javafx.fxml, javafx.graphics;
    exports com.qualibits.qualeval.mode;
    exports com.qualibits.qualeval.exec;
    opens com.qualibits.qualeval.exec to javafx.fxml, javafx.graphics;
}