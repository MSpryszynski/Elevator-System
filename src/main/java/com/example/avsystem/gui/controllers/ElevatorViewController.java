package com.example.avsystem.gui.controllers;

import com.example.avsystem.gui.utils.ElevatorRectangleFactory;
import com.example.avsystem.gui.JavaFxApplication;
import com.example.avsystem.gui.buttons.DownButton;
import com.example.avsystem.gui.buttons.PickupButton;
import com.example.avsystem.gui.buttons.UpButton;
import com.example.avsystem.model.ElevatorStatus;
import com.example.avsystem.model.ElevatorSystem;
import com.example.avsystem.model.PickupRequest;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Controller;

import java.util.Map;


@Controller
public class ElevatorViewController {

    @FXML
    private GridPane systemGrid;

    private int numberOfFloors;

    private int numberOfElevators;

    private final StartViewController startViewController;

    private final PickupViewController pickupViewController;

    private final ElevatorSystem elevatorSystem;

    private final ElevatorRectangleFactory elevatorRectangleFactory;


    public ElevatorViewController(StartViewController startViewController, PickupViewController pickupViewController, ElevatorSystem elevatorSystem,
                                  ElevatorRectangleFactory elevatorRectangleFactory) {
        this.startViewController = startViewController;
        this.pickupViewController = pickupViewController;
        this.elevatorSystem = elevatorSystem;
        this.elevatorRectangleFactory = elevatorRectangleFactory;
    }

    @FXML
    public void initialize() {
        numberOfFloors = startViewController.getNumberOfFloors();
        numberOfElevators = startViewController.getNumberOfElevators();
        initializeRectangleFactory();
        createElevatorSystemGrid();
        createPickupGrid();
    }

    private void initializeRectangleFactory() {
        int gridWidthPadding = 150;
        double elevatorWidth = (systemGrid.getPrefWidth() - gridWidthPadding * 2) / numberOfElevators;
        elevatorRectangleFactory.setElevatorWidth(elevatorWidth);
        int gridHeightPadding = 25;
        double elevatorHeight = (systemGrid.getPrefHeight() - gridHeightPadding * 2) / (numberOfFloors + 1);
        elevatorRectangleFactory.setElevatorHeight(elevatorHeight);
    }


    private void createElevatorSystemGrid() {
        createEmptyElevatorSystemGrid();
        Map<Integer, ElevatorStatus> elevatorSystemStatus = elevatorSystem.status();
        showElevatorSystemStatusOnGrid(elevatorSystemStatus);
    }

    private void createEmptyElevatorSystemGrid() {
        for (int i = 0; i < numberOfElevators; i++) {
            for (int j = 0; j < numberOfFloors + 1; j++) {
                Rectangle elevatorRect = elevatorRectangleFactory.createEmptyRectangle();
                systemGrid.add(elevatorRect, i, j);
            }
        }
    }

    private void showElevatorSystemStatusOnGrid(Map<Integer, ElevatorStatus> status) {
        for (Map.Entry<Integer, ElevatorStatus> entry : status.entrySet()) {
            int elevatorId = entry.getKey();
            ElevatorStatus elevatorStatus = entry.getValue();
            Rectangle elevatorRectangle;
            if (elevatorStatus.isOnPickupFloor()) {
                elevatorRectangle = elevatorRectangleFactory.createWaitingElevatorRectangle();
            } else if (elevatorStatus.isOnTargetFloor()) {
                elevatorRectangle = elevatorRectangleFactory.createWaitingElevatorRectangle();
            } else if (elevatorStatus.getPickupFloor().isPresent()) {
                elevatorRectangle = elevatorRectangleFactory.createPickedElevatorRectangle();
            } else if (elevatorStatus.getTargetFloor().isPresent()) {
                elevatorRectangle = elevatorRectangleFactory.createTargetGoingElevatorRectangle();
            } else {
                elevatorRectangle = elevatorRectangleFactory.createFreeElevatorRectangle();
            }
            systemGrid.add(elevatorRectangle, elevatorId, numberOfFloors - elevatorStatus.getCurrentFloor());
        }
    }

    private void createPickupGrid() {
        for (int i = 0; i < numberOfFloors; i++) {
            PickupButton upButton = new UpButton(i);
            upButton.setOnAction(event -> elevatorSystem.pickup(new PickupRequest(upButton.getFloorNumber(), upButton.getElevatorDirection())));
            systemGrid.add(upButton, numberOfElevators, numberOfFloors - i);
        }
        for (int i = 1; i < numberOfFloors + 1; i++) {
            PickupButton downButton = new DownButton(i);
            downButton.setOnAction(event -> elevatorSystem.pickup(new PickupRequest(downButton.getFloorNumber(), downButton.getElevatorDirection())));
            systemGrid.add(downButton, numberOfElevators + 1, numberOfFloors - i);
        }
    }

    @FXML
    private void simulateStep() {
        elevatorSystem.step();
        createElevatorSystemGrid();
        askUserForTargetFloors();
    }

    private void askUserForTargetFloors() {
        Map<Integer, ElevatorStatus> elevatorSystemStatus = elevatorSystem.status();
        boolean sceneChanged = false;
        for (ElevatorStatus elevatorStatus : elevatorSystemStatus.values()) { ;
            if (elevatorStatus.isOnPickupFloor()) {
                if (!sceneChanged) {
                    JavaFxApplication.setScene("/view/PickupView.fxml");
                    sceneChanged = true;
                }
                pickupViewController.askUserForTargetFloor(elevatorStatus);
            }
        }
    }
}
