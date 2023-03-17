package com.example.avsystem.gui.utils;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Component;

@Component
public class ElevatorRectangleFactory {

    private double elevatorWidth;
    private double elevatorHeight;


    public Rectangle createEmptyRectangle() { return createElevatorRectangle(Paint.valueOf("white")); }

    public Rectangle createFreeElevatorRectangle() {
        return createElevatorRectangle(Paint.valueOf("green"));
    }

    public Rectangle createPickedElevatorRectangle() {
        return createElevatorRectangle(Paint.valueOf("yellow"));
    }

    public Rectangle createWaitingElevatorRectangle() { return createElevatorRectangle(Paint.valueOf("red")); }

    public Rectangle createTargetGoingElevatorRectangle() {return createElevatorRectangle(Paint.valueOf("blue"));}


    public void setElevatorWidth(double elevatorWidth) {
        this.elevatorWidth = elevatorWidth;
    }

    public void setElevatorHeight(double elevatorHeight) {
        this.elevatorHeight = elevatorHeight;
    }


    private Rectangle createElevatorRectangle(Paint color) {
        return new Rectangle(elevatorWidth, elevatorHeight, color);
    }
}
