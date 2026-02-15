package com.pulsedesk.comment_processor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;

public class PulseDeskApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Pulse Desk");
        stage.setScene(scene);
        stage.show();

        Platform.setImplicitExit(true);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }
}