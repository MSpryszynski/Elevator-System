package com.example.avsystem.gui;

import com.example.avsystem.AvSystemApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class JavaFxApplication extends Application {

    public static ConfigurableApplicationContext context;

    private static Stage stage;

    @Override
    public void init() throws Exception {
        super.init();
        context = SpringApplication.run(AvSystemApplication.class);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Elevator system");
        setScene("/view/StartView.fxml");
    }

    public static void setScene(String path) {
        URL fxmlPath = Objects.requireNonNull(JavaFxApplication.class.getResource(path));
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath);
        fxmlLoader.setControllerFactory(context::getBean);
        try {
            stage.setScene(new javafx.scene.Scene(fxmlLoader.load()));
        } catch (IOException ex) {
            throw new RuntimeException("There is an error while loading fxml from path: " + fxmlPath.getPath(), ex);
        }
        stage.show();
    }
}
