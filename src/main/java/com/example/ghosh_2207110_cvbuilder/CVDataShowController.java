package com.example.ghosh_2207110_cvbuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class CVDataShowController {

    @FXML private TableView<PersonCV> cvTable;
    @FXML private TableColumn<PersonCV, Long> idColumn;
    @FXML private TableColumn<PersonCV, String> nameColumn;
    @FXML private TableColumn<PersonCV, String> emailColumn;
    @FXML private TableColumn<PersonCV, String> phoneColumn;
    @FXML private TableColumn<PersonCV, String> addressColumn;
    @FXML private TableColumn<PersonCV, String> educationColumn;
    @FXML private TableColumn<PersonCV, String> skillsColumn;
    @FXML private TableColumn<PersonCV, String> workColumn;
    @FXML private TableColumn<PersonCV, String> projectColumn;
    @FXML private TableColumn<PersonCV, Void> actionColumn;

    private final ObservableList<PersonCV> data = FXCollections.observableArrayList();
    private final CVRepository repository = CVRepository.getInstance();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        educationColumn.setCellValueFactory(new PropertyValueFactory<>("education"));
        skillsColumn.setCellValueFactory(new PropertyValueFactory<>("skills"));
        workColumn.setCellValueFactory(new PropertyValueFactory<>("work"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));

        cvTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addActionButtons();
        loadAllCV();
    }

    private void loadAllCV() {
        repository.getAllAsync(this::onLoadSuccess, this::onError);
    }

    private void onLoadSuccess(List<PersonCV> list) {
        data.setAll(list);
        cvTable.setItems(data);
    }

    private void onError(Throwable ex) {
        ex.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
        alert.setHeaderText("Error");
        alert.showAndWait();
    }

    private void addActionButtons() {
        CVDataShowController outer = this;

        actionColumn.setCellFactory(param -> new TableCell<PersonCV, Void>() {
            private final Button previewBtn = new Button("Preview");
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox topRow = new HBox(5, previewBtn, editBtn);
            private final VBox pane = new VBox(5, topRow, deleteBtn);

            {
                previewBtn.setOnAction(e -> {
                    PersonCV cv = (PersonCV) getTableRow().getItem();
                    if (cv == null) return;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("showCV.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);
                        ShowCVController controller = loader.getController();
                        controller.initData(cv);
                        stage.show();
                    } catch (IOException ioEx) {
                        ioEx.printStackTrace();
                    }
                });

                editBtn.setOnAction(e -> {
                    PersonCV cv = (PersonCV) getTableRow().getItem();
                    if (cv == null) return;

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);

                        FormController controller = loader.getController();
                        controller.loadForEdit(cv, () -> {
                            repository.getAllAsync(list -> {
                                data.setAll(list);
                                cvTable.setItems(data);
                            }, ex -> ex.printStackTrace());
                        });

                        stage.show();

                    } catch (IOException ioEx) {
                        ioEx.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Failed to open form: " + ioEx);
                        alert.showAndWait();
                    }
                });

                deleteBtn.setOnAction(e -> {
                    PersonCV cv = (PersonCV) getTableRow().getItem();
                    if (cv == null) return;
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                            "Delete record #" + cv.getId() + " (" + cv.getName() + ")?",
                            ButtonType.YES, ButtonType.NO);
                    confirm.showAndWait().ifPresent(btn -> {
                        if (btn == ButtonType.YES) {
                            outer.repository.deleteAsync((int) cv.getId(), () -> {
                                data.remove(cv);
                            }, outer::onError);
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

    @FXML
    private void onAddNewCV() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
            Stage stage = (Stage) cvTable.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to open form: " + e);
            alert.showAndWait();
        }
    }
}
