package com.example.ghosh_2207110_cvbuilder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ShowCVController {

    @FXML
    private ImageView profileImageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label jobLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label educationLabel;
    @FXML
    private Label skillsLabel;
    @FXML
    private Label workLabel;
    @FXML
    private Label projectsLabel;

    public void initData(CVData data) {
        nameLabel.setText(data.getFullName());
        emailLabel.setText(data.getEmail());
        phoneLabel.setText(data.getPhone());
        addressLabel.setText(data.getAddress());
        educationLabel.setText(data.getEducation());
        skillsLabel.setText(data.getSkills());
        workLabel.setText(data.getExperience());
        projectsLabel.setText(data.getProjects());
    }

    public static class CVData {
        private final String fullName;
        private final String email;
        private final String phone;
        private final String address;
        private final String education;
        private final String skills;
        private final String experience;
        private final String projects;

        public CVData(String fullName, String email, String phone, String address,
                      String education, String skills, String experience, String projects) {
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.education = education;
            this.skills = skills;
            this.experience = experience;
            this.projects = projects;
        }

        public String getFullName() {
            return fullName; }
        public String getEmail() {
            return email; }
        public String getPhone() {
            return phone; }
        public String getAddress() {
            return address; }
        public String getEducation() {
            return education; }
        public String getSkills() {
            return skills; }
        public String getExperience() {
            return experience; }
        public String getProjects() {
            return projects; }
    }
}
