package com.example.avsystem.model;


public class Elevator {

    private final int id;

    private ElevatorStatus elevatorStatus;


    public Elevator(int id) {
        this.id = id;
        this.elevatorStatus = new ElevatorStatus();
    }

    public Elevator(int id, ElevatorStatus elevatorStatus) {
        this.id = id;
        this.elevatorStatus = elevatorStatus;
    }

    public int getId() {
        return id;
    }

    public ElevatorStatus getElevatorStatus() {
        return elevatorStatus;
    }

    public void setElevatorStatus(ElevatorStatus elevatorStatus) {
        this.elevatorStatus = elevatorStatus;
    }

}
