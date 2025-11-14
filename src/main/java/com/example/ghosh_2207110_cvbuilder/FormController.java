package com.example.ghosh_2207110_cvbuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FormController {

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputEmail;

    @FXML
    private TextArea inputNumber;

    @FXML
    private TextArea inputAdd;

    @FXML
    private TextArea inputEducation;

    @FXML
    private TextArea inputSkills;

    @FXML
    private TextArea inputWork;

    @FXML
    private TextArea inputProject;

    @FXML
    void OnbtnClkBuildCv(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showcv.fxml"));
        Scene scene = new Scene(loader.load());
        ShowCVController controller = loader.getController();

        ShowCVController.CVData data = new ShowCVController.CVData(
                inputName.getText(),
                inputEmail.getText(),
                inputNumber.getText(),
                inputAdd.getText(),
                inputEducation.getText(),
                inputSkills.getText(),
                inputWork.getText(),
                inputProject.getText()
        );

        controller.initData(data);

        Stage stage = (Stage) inputName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("CV saved successfully!");
        alert.showAndWait();
    }
}
