package com.example.avsystem.model;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ElevatorSimulationService {

    public void simulateElevatorStep(Elevator elevator) {
        ElevatorStatus elevatorStatus = elevator.getElevatorStatus();
        if (elevatorIsWaiting(elevatorStatus)) {
            return;
        }
        Optional<Integer> pickupFloor = elevatorStatus.getPickupFloor();
        if (pickupFloor.isPresent()) {
            elevatorStatus.moveInPickupFloorDirection();
            return;
        }
        simulateStepDependingOnTargetFloor(elevatorStatus);
    }

    private boolean elevatorIsWaiting(ElevatorStatus elevatorStatus) {
        if (elevatorStatus.isOnPickupFloor()) {
            elevatorStatus.leavePickupFloor();
            return true;
        }
        if (elevatorStatus.isOnTargetFloor()) {
            elevatorStatus.leaveTargetFloor();
            return true;
        }
        return false;
    }

    private void simulateStepDependingOnTargetFloor(ElevatorStatus elevatorStatus) {
        Optional<Integer> elevatorTargetFloor = elevatorStatus.getTargetFloor();
        if (elevatorTargetFloor.isPresent()) {
            elevatorStatus.moveInTargetDirection();
        }
    }
}
