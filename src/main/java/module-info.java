module com.qualibits.qualeval{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.logging;

    opens com.qualibits.qualeval to javafx.graphics, javafx.fxml;
    exports com.qualibits.qualeval;
    exports com.qualibits.qualeval.term.operator;
    opens com.qualibits.qualeval.term.operator to javafx.fxml, javafx.graphics;
    exports com.qualibits.qualeval.term;
    opens com.qualibits.qualeval.term to javafx.fxml, javafx.graphics;
    exports com.qualibits.qualeval.buttons;
    opens com.qualibits.qualeval.buttons to javafx.fxml, javafx.graphics;
    exports com.qualibits.qualeval.mode;
    opens com.qualibits.qualeval.mode to javafx.fxml, javafx.graphics;
    exports com.qualibits.qualeval.exec;
    opens com.qualibits.qualeval.exec to javafx.fxml, javafx.graphics;
    exports com.qualibits.qualeval.dialoglayout;
    opens com.qualibits.qualeval.dialoglayout to javafx.fxml, javafx.graphics;
}