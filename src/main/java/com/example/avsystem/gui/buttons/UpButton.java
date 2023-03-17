package com.example.avsystem.gui.buttons;

import com.example.avsystem.model.ElevatorDirection;

public class UpButton extends PickupButton {


    public UpButton(int floorNumber) {
        super(floorNumber);
        setText("UP");
        setElevatorDirection(ElevatorDirection.UP);
    }
}
