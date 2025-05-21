module com.varcal {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;

    opens com.varcal.controller to javafx.fxml;
    exports com.varcal;
}
