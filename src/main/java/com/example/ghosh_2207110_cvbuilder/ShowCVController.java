package com.example.ghosh_2207110_cvbuilder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

public class ShowCVController {

    @FXML private ImageView profileImageView;
    @FXML private Label nameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label addressLabel;
    @FXML private Label educationLabel;
    @FXML private Label skillsLabel;
    @FXML private Label workLabel;
    @FXML private Label projectsLabel;

    public void initData(PersonCV cv) {
        nameLabel.setText(cv.getName());
        emailLabel.setText(cv.getEmail());
        phoneLabel.setText(cv.getPhone());
        addressLabel.setText(cv.getAddress());
        educationLabel.setText(toBullets(cv.getEducation()));
        skillsLabel.setText(toBullets(cv.getSkills()));
        workLabel.setText(toBullets(cv.getWork()));
        projectsLabel.setText(toBullets(cv.getProject()));

        if (cv.getImagePath() != null && !cv.getImagePath().isEmpty()) {
            try {
                Image img = new Image(cv.getImagePath(), true);
                profileImageView.setImage(img);

                double min = Math.min(img.getWidth(), img.getHeight());
                profileImageView.setViewport(new Rectangle2D(
                        (img.getWidth() - min) / 2,
                        (img.getHeight() - min) / 2,
                        min, min
                ));
            } catch (Exception e) {
                profileImageView.setImage(null);
            }
        } else {
            profileImageView.setImage(null);
        }
    }

    private String toBullets(String data) {
        if (data == null || data.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (String item : data.split(",")) {
            sb.append("• ").append(item.trim()).append("\n");
        }
        return sb.toString();
    }
}
