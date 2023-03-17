package com.example.avsystem.model;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MyElevatorSystem implements ElevatorSystem {

    private final List<Elevator> elevators = new LinkedList<>();

    private final List<PickupRequest> omittedPickupRequests = new LinkedList<>();

    private int numberOfFloors;

    private final ElevatorSimulationService elevatorSimulationService;

    private final ElevatorPickupService elevatorPickupService;

    private final ElevatorOptimizationService elevatorPositionService;


    public MyElevatorSystem(ElevatorSimulationService elevatorSimulationService, ElevatorPickupService elevatorPickupService,
                            ElevatorOptimizationService elevatorPositionService) {
        this.elevatorSimulationService = elevatorSimulationService;
        this.elevatorPickupService = elevatorPickupService;
        this.elevatorPositionService = elevatorPositionService;
    }

    @Override
    public Map<Integer, ElevatorStatus> status() {
        return elevators.stream().collect(Collectors.toMap(Elevator::getId, Elevator::getElevatorStatus));
    }

    @Override
    public void update(int elevatorId, ElevatorStatus elevatorStatus) {
        Elevator elevator = elevators.get(elevatorId);
        elevator.setElevatorStatus(elevatorStatus);
    }

    @Override
    public void pickup(PickupRequest pickupRequest) {
        Optional<Elevator> calledElevator = elevatorPickupService.findNearestPossibleElevator(elevators, pickupRequest);
        if (calledElevator.isPresent()) {
            elevatorPickupService.pickupElevator(calledElevator.get(), pickupRequest);
        } else {
            omittedPickupRequests.add(pickupRequest);
        }
    }

    @Override
    public void step() {
        pickupOmittedRequests();
        simulateElevatorsSteps();
        optimizeEmptyElevators();
    }

    private void pickupOmittedRequests() {
        for (int i = omittedPickupRequests.size() - 1; i >= 0; --i) {
            PickupRequest omittedPickupRequest = omittedPickupRequests.remove(i);
            pickup(omittedPickupRequest);
        }
    }

    private void simulateElevatorsSteps() {
        for (Elevator elevator : elevators) {
            elevatorSimulationService.simulateElevatorStep(elevator);
        }
    }

    private void optimizeEmptyElevators() {
        List<Elevator> emptyElevators = elevators.stream()
                .filter(elevator -> elevator.getElevatorStatus().isFree())
                .collect(Collectors.toList());
        elevatorPositionService.configureEmptyElevators(emptyElevators, numberOfFloors);
    }

    @Override
    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    @Override
    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

}
