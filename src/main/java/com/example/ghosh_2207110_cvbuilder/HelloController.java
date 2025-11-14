package com.example.ghosh_2207110_cvbuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button BtnCeateCv;

    @FXML
    private Label welcomeLabel;

    @FXML
    void OnClickCreateCv(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("form.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        Stage stage=(Stage) (welcomeLabel.getScene().getWindow());
        stage.setScene(scene);
        stage.show();

    }

}
