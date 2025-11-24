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
    private final CVRepository repository = CVRepository.getInstance();

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
    void onBtnBuildCV(ActionEvent event) {
        String name = inputName.getText();
        String email = inputEmail.getText();
        String phone = inputNumber.getText();
        String address = inputAdd.getText();
        String education = inputEducation.getText();
        String skills = inputSkills.getText();
        String work = inputWork.getText();
        String project = inputProject.getText();
        String imagePath = selectedImageFile != null ? selectedImageFile.toURI().toString() : null;

        repository.insertAsync(
                name, email, phone, address, education, skills, work, project, imagePath,
                insertedCV -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("showcv.fxml"));
                        Stage stage = (Stage) inputName.getScene().getWindow();
                        stage.setScene(new Scene(loader.load()));

                        ShowCVController controller = loader.getController();
                        controller.initData(insertedCV);

                        stage.show();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setHeaderText(null);
                        alert.setContentText("CV saved successfully!");
                        alert.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert err = new Alert(Alert.AlertType.ERROR,
                                "Failed to load showcv.fxml: " + e.toString());
                        err.showAndWait();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to save CV: " + error.toString());
                    alert.showAndWait();
                }
        );
    }
}
