module com.example.ghosh_2207110_cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    opens com.example.ghosh_2207110_cvbuilder to javafx.fxml;
    exports com.example.ghosh_2207110_cvbuilder;
}
