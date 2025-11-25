package com.example.ghosh_2207110_cvbuilder;

import javafx.application.Platform;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class CVRepository {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final CVRepository INSTANCE = new CVRepository();

    private CVRepository() {
        DataBaseHelper.initDataBase();
    }

    public static CVRepository getInstance() {
        return INSTANCE;
    }

    public void getAllAsync(Consumer<List<PersonCV>> onSuccess, Consumer<Throwable> onError) {
        executor.submit(() -> {
            try {
                List<PersonCV> list = DataBaseHelper.getAllCV();
                Platform.runLater(() -> onSuccess.accept(list));
            } catch (Throwable ex) {
                Platform.runLater(() -> onError.accept(ex));
            }
        });
    }

    public void insertAsync(String name, String email, String phone, String address,
                            String education, String skills, String work, String project,
                            String imagePath, Consumer<PersonCV> onSuccess, Consumer<Throwable> onError) {
        executor.submit(() -> {
            try {
                PersonCV cv = DataBaseHelper.insertCV(name, email, phone, address,
                        education, skills, work, project, imagePath);
                Platform.runLater(() -> onSuccess.accept(cv));
            } catch (Throwable ex) {
                Platform.runLater(() -> onError.accept(ex));
            }
        });
    }

    public void updateAsync(PersonCV cv, Runnable onSuccess, Consumer<Throwable> onError) {
        executor.submit(() -> {
            try {
                DataBaseHelper.updateCV(cv);
                Platform.runLater(onSuccess);
            } catch (Throwable ex) {
                Platform.runLater(() -> onError.accept(ex));
            }
        });
    }

    public void deleteAsync(long id, Runnable onSuccess, Consumer<Throwable> onError) {
        executor.submit(() -> {
            try {
                DataBaseHelper.deleteCV(id);
                Platform.runLater(onSuccess);
            } catch (Throwable ex) {
                Platform.runLater(() -> onError.accept(ex));
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
