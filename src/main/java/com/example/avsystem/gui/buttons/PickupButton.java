package com.example.avsystem.gui.buttons;

import com.example.avsystem.model.ElevatorDirection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public abstract class PickupButton extends Button {

    private int floorNumber;

    private ElevatorDirection elevatorDirection;

    public PickupButton(int floorNumber) {
        this.floorNumber = floorNumber;
        maxHeight(floorNumber);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ElevatorDirection getElevatorDirection() {
        return elevatorDirection;
    }

    protected void setElevatorDirection(ElevatorDirection elevatorDirection) {
        this.elevatorDirection = elevatorDirection;
    }
}
