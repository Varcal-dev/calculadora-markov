module com.varcal {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;

    opens com.varcal to javafx.fxml;

    exports com.varcal;
}
