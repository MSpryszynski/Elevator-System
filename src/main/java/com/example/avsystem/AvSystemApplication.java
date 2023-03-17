package com.example.avsystem;

import com.example.avsystem.gui.JavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvSystemApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }

}
