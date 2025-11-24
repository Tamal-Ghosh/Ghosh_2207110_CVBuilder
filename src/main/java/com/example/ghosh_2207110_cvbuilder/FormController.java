package com.example.ghosh_2207110_cvbuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FormController {

    @FXML private TextField inputName;
    @FXML private TextField inputEmail;
    @FXML private TextArea inputNumber;
    @FXML private TextArea inputAdd;
    @FXML private TextArea inputEducation;
    @FXML private TextArea inputSkills;
    @FXML private TextArea inputWork;
    @FXML private TextArea inputProject;
    @FXML private ImageView profileImageView;

    private File selectedImageFile;

    @FXML
    void onChooseImage(ActionEvent event) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Profile Picture");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedImageFile = chooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            profileImageView.setImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

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
                inputProject.getText(),
                selectedImageFile != null ? selectedImageFile.toURI().toString() : null
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