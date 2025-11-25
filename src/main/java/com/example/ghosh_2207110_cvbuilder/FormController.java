package com.example.ghosh_2207110_cvbuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    @FXML private Button btCVBuild;

    private File selectedImageFile;
    private final CVRepository repository = CVRepository.getInstance();
    private PersonCV updateCV = null;
    private boolean editMode = false;
    private Runnable onUpdateCallback = null;

    public void loadForEdit(PersonCV cv, Runnable onUpdateCallback) {
        editMode = true;
        updateCV = cv;
        this.onUpdateCallback = onUpdateCallback;

        inputName.setText(cv.getName());
        inputEmail.setText(cv.getEmail());
        inputNumber.setText(cv.getPhone());
        inputAdd.setText(cv.getAddress());
        inputEducation.setText(cv.getEducation());
        inputSkills.setText(cv.getSkills());
        inputWork.setText(cv.getWork());
        inputProject.setText(cv.getProject());

        if (cv.getImagePath() != null) {
            try { profileImageView.setImage(new Image(cv.getImagePath())); } catch (Exception ignore) {}
        }

        btCVBuild.setText("Update CV");
    }

    @FXML
    void onChooseImage(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Profile Picture");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = chooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            profileImageView.setImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

    @FXML
    void onBtnBuildCV(ActionEvent event) {
        if (editMode) updateExistingCV();
        else insertCV();
    }

    private void insertCV() {
        String imagePath = selectedImageFile != null ? selectedImageFile.toURI().toString() : null;

        repository.insertAsync(
                inputName.getText(),
                inputEmail.getText(),
                inputNumber.getText(),
                inputAdd.getText(),
                inputEducation.getText(),
                inputSkills.getText(),
                inputWork.getText(),
                inputProject.getText(),
                imagePath,
                insertedCV -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("showcv.fxml"));
                        Stage stage = (Stage) inputName.getScene().getWindow();
                        stage.setScene(new Scene(loader.load()));
                        ShowCVController controller = loader.getController();
                        controller.initData(insertedCV);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setHeaderText(null);
                        alert.setContentText("CV saved successfully!");
                        alert.showAndWait();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert err = new Alert(Alert.AlertType.ERROR, "Failed to load showcv.fxml: " + e);
                        err.showAndWait();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to save CV: " + error);
                    alert.showAndWait();
                }
        );
    }

    private void updateExistingCV() {
        PersonCV updated = new PersonCV(
                updateCV.getId(),
                inputName.getText(),
                inputEmail.getText(),
                inputNumber.getText(),
                inputAdd.getText(),
                inputEducation.getText(),
                inputSkills.getText(),
                inputWork.getText(),
                inputProject.getText(),
                selectedImageFile != null ? selectedImageFile.toURI().toString() : updateCV.getImagePath()
        );

        repository.updateAsync(updated, () -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("CV updated successfully!");
            alert.showAndWait();

            if (onUpdateCallback != null) onUpdateCallback.run();

            Stage stage = (Stage) btCVBuild.getScene().getWindow();
            stage.close();

        }, error -> {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Update failed: " + error);
            a.showAndWait();
        });
    }
}
