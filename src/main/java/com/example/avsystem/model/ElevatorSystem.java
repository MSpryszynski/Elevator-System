package com.example.avsystem.model;

import java.util.Map;

public interface ElevatorSystem {


    /**
     * Gets current elevator system configuration
     * @return elevatorID -> elevatorStatus (current floor and target floor) map
     */
    Map<Integer, ElevatorStatus> status();


    /**
     * Updates elevator status of an elevator with given ID;
     */
    void update(int elevatorId, ElevatorStatus elevatorStatus);


    /**
     * Signals that someone called elevator on given floor in specified direction
     */
    void pickup(PickupRequest pickupRequest);


    /**
     * Simulates one step of a system
     */
    void step();

    /**
     * Adds new elevator to a system
     */
    void addElevator(Elevator elevator);

    /**
     * Set number of floors int the building
     */
    void setNumberOfFloors(int numberOfFloors);
}
