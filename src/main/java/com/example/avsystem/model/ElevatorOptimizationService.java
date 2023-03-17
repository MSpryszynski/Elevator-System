package com.example.avsystem.model;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
public class ElevatorOptimizationService {

    public void configureEmptyElevators(List<Elevator> freeElevators, int numberOfFloors) {
        int numberOfElevators = freeElevators.size();
        freeElevators.sort(Comparator.comparingInt(o -> o.getElevatorStatus().getCurrentFloor()));
        for (int i = 0; i < numberOfElevators; i++) {
            int desiredFloor = numberOfFloors * i / numberOfElevators;
            freeElevators.get(i).getElevatorStatus().moveInFloorDirection(desiredFloor);
        }
    }
}
