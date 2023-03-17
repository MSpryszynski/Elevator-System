package com.example.avsystem.gui.controllers;


import com.example.avsystem.gui.JavaFxApplication;
import com.example.avsystem.gui.utils.Prompts;
import com.example.avsystem.gui.utils.TargetFloorResponse;
import com.example.avsystem.model.ElevatorDirection;
import com.example.avsystem.model.ElevatorStatus;
import com.sun.javafx.scene.control.IntegerField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.List;

@Controller
public class PickupViewController {

    @FXML
    private IntegerField targetFloorField;

    @FXML
    private Label directionLabel;

    @FXML
    private Label currentFloorLabel;

    private final List<ElevatorStatus> waitingElevators = new LinkedList<>();

    private ElevatorStatus currentElevator;

    private final StartViewController startViewController;

    public PickupViewController(StartViewController startViewController) {
        this.startViewController = startViewController;
    }

    public void askUserForTargetFloor(ElevatorStatus elevatorStatus) {
        if (currentElevator == null) {
            bindElevatorStatus(elevatorStatus);
        } else {
            waitingElevators.add(elevatorStatus);
        }
    }

    private void bindElevatorStatus(ElevatorStatus elevatorStatus) {
        currentElevator = elevatorStatus;
        setElevatorDirectionLabel(elevatorStatus.getPickedDirection());
        setCurrentFloorLabel(elevatorStatus.getCurrentFloor());
    }

    private void setElevatorDirectionLabel(ElevatorDirection elevatorDirection) {
        String directionText = "";
        if (elevatorDirection == ElevatorDirection.DOWN) {
            directionText = "Elevator goes down";
        } else if (elevatorDirection == ElevatorDirection.UP) {
            directionText = "Elevator goes up";
        }
        directionLabel.setText(directionText);
    }

    private void setCurrentFloorLabel(int currentFloor) {
        currentFloorLabel.setText("Floor " + currentFloor);
        targetFloorField.setValue(currentFloor);
    }

    @FXML
    private void addTargetFloor() {
        TargetFloorResponse response;
        int requestedTargetFloor = targetFloorField.getValue();
        if (requestedTargetFloor > startViewController.getNumberOfFloors()) {
            response = TargetFloorResponse.INVALID_FLOOR;
        } else {
            response = currentElevator.addTargetFloor(targetFloorField.getValue());
        }
        showTargetFloorResponsePrompt(response);
        setCurrentFloorLabel(currentElevator.getCurrentFloor());
    }

    private void showTargetFloorResponsePrompt(TargetFloorResponse targetFloorResponse) {
        switch (targetFloorResponse) {
            case SUCCESS -> Prompts.targetFloorAdded();
            case ALREADY_PICKED -> Prompts.targetFloorAlreadyPicked();
            case INVALID_DIRECTION -> Prompts.wrongTargetFloorDirection();
            case INVALID_FLOOR -> Prompts.wrongTargetFloor();
        }
    }

    @FXML
    private void close() {
        Prompts.elevatorMoves(directionLabel.getText());
        currentElevator = null;
        if (waitingElevators.isEmpty()) {
            JavaFxApplication.setScene("/view/ElevatorView.fxml");
        } else {
            bindElevatorStatus(waitingElevators.remove(0));
        }
    }

}
