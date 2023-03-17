package com.example.avsystem.gui.buttons;

import com.example.avsystem.model.ElevatorDirection;

public class DownButton extends PickupButton {


    public DownButton(int floorNumber) {
        super(floorNumber);
        setText("DOWN");
        setElevatorDirection(ElevatorDirection.DOWN);
    }

}
