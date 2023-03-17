package com.example.avsystem.model;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ElevatorPickupService {

    public Optional<Elevator> findNearestPossibleElevator(List<Elevator> elevators, PickupRequest pickupRequest) {
        List<Elevator> sortedElevators = sortElevatorsByLengthToPickupFloor(elevators, pickupRequest.pickupFloor());
        for (Elevator elevator : sortedElevators) {
            ElevatorStatus elevatorStatus = elevator.getElevatorStatus();
            if (elevatorIsFree(elevatorStatus)) {
                return Optional.of(elevator);
            }
            if (elevatorGoingThroughPickupFloorInSignalizedDirection(elevatorStatus, pickupRequest)) {
                return Optional.of(elevator);
            }
        }
        return Optional.empty();
    }

    private List<Elevator> sortElevatorsByLengthToPickupFloor(List<Elevator> elevators, int pickupFloor) {
        return elevators.stream()
                .sorted(Comparator.comparing(elevator -> getDifferenceBetweenElevatorAndPickupFloors(elevator, pickupFloor)))
                .collect(Collectors.toList());
    }

    private Integer getDifferenceBetweenElevatorAndPickupFloors(Elevator elevator, int pickupFloor) {
        return Math.abs(elevator.getElevatorStatus().getCurrentFloor()-pickupFloor);
    }

    private boolean elevatorIsFree(ElevatorStatus elevatorStatus) {
        return elevatorStatus.getTargetFloor().isEmpty() && elevatorStatus.getPickupFloor().isEmpty();
    }

    private boolean elevatorGoingThroughPickupFloorInSignalizedDirection(ElevatorStatus elevatorStatus, PickupRequest pickupRequest) {
        Optional<Integer> currentPickupFloor = elevatorStatus.getPickupFloor();
        if (currentPickupFloor.isEmpty()) {
            return elevatorStatus.getPickedDirection().equals(pickupRequest.signalizedDirection())
                    && elevatorStatus.validTargetDirection(pickupRequest.pickupFloor());
        }
        return elevatorStatus.getPickedDirection().equals(pickupRequest.signalizedDirection())
                && elevatorStatus.validTargetDirection(pickupRequest.pickupFloor())
                && elevatorStatus.validTargetDirection(currentPickupFloor.get());
    }

    public void pickupElevator(Elevator elevator, PickupRequest pickupRequest) {
        ElevatorStatus elevatorStatus = elevator.getElevatorStatus();
        Optional<Integer> currentPickupFloor = elevatorStatus.getPickupFloor();
        Optional<Integer> targetFloor = elevatorStatus.getTargetFloor();
        if (targetFloor.isPresent() || (currentPickupFloor.isPresent() && currentPickupFloor.get() != pickupRequest.pickupFloor())) {
            elevatorStatus.addExtraPickupFloor(pickupRequest.pickupFloor());
            return;
        }
        elevatorStatus.pickup(pickupRequest);
    }


}
