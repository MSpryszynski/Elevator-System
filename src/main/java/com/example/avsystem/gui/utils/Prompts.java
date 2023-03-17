package com.example.avsystem.gui.utils;


import javafx.scene.control.Alert;

public class Prompts {


    public static void targetFloorAdded() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Target floor added successfully!");
        alert.showAndWait();
    }

    public static void targetFloorAlreadyPicked() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("This target floor has already been picked!");
        alert.showAndWait();
    }

    public static void wrongTargetFloorDirection() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("This target floor doesn't match signalized direction!");
        alert.showAndWait();
    }

    public static void wrongTargetFloor() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Invalid target floor!");
        alert.showAndWait();
    }

    public static void elevatorMoves(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
