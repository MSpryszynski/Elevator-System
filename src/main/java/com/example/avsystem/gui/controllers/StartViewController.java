package com.example.avsystem.gui.controllers;

import com.example.avsystem.gui.JavaFxApplication;
import com.example.avsystem.model.Elevator;
import com.example.avsystem.model.ElevatorSystem;
import com.sun.javafx.scene.control.IntegerField;
import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;

@Controller
public class StartViewController {

    @FXML
    private IntegerField floorField;

    @FXML
    private IntegerField elevatorField;

    private final ElevatorSystem elevatorSystem;


    public StartViewController(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }


    @FXML
    public void start() {
        for (int i = 0; i < elevatorField.getValue(); i++) {
            elevatorSystem.addElevator(new Elevator(i));
        }
        elevatorSystem.setNumberOfFloors(floorField.getValue());
        JavaFxApplication.setScene("/view/ElevatorView.fxml");
    }

    public int getNumberOfFloors() {
        return floorField.getValue();
    }

    public int getNumberOfElevators() {
        return elevatorField.getValue();
    }
}

