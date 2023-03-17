package com.example.avsystem.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorOptimizationServiceTest {

    private final ElevatorOptimizationService elevatorOptimizationService = new ElevatorOptimizationService();

    @Test
    public void elevatorsShouldStayAtSamePlace() {
        //given
        ElevatorStatus elevatorStatus1 = new ElevatorStatus(0, null, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        ElevatorStatus elevatorStatus2 = new ElevatorStatus(4, null, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        List<Elevator> elevators = new LinkedList<>(List.of(new Elevator(2, elevatorStatus1), new Elevator(233, elevatorStatus2)));

        //when
        elevatorOptimizationService.configureEmptyElevators(elevators, 8);

        //then
        assertEquals(0, elevators.get(0).getElevatorStatus().getCurrentFloor());
        assertEquals(4, elevators.get(1).getElevatorStatus().getCurrentFloor());
    }

    @Test
    public void oneElevatorShouldMove() {
        //given
        ElevatorStatus elevatorStatus1 = new ElevatorStatus(0, null, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        ElevatorStatus elevatorStatus2 = new ElevatorStatus(0, null, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        List<Elevator> elevators = new LinkedList<>(List.of(new Elevator(2, elevatorStatus1), new Elevator(233, elevatorStatus2)));

        //when
        elevatorOptimizationService.configureEmptyElevators(elevators, 8);

        //then
        assertEquals(0, elevators.get(0).getElevatorStatus().getCurrentFloor());
        assertEquals(1, elevators.get(1).getElevatorStatus().getCurrentFloor());
    }

    @Test
    public void bothElevatorsShouldMove() {
        //given
        ElevatorStatus elevatorStatus1 = new ElevatorStatus(1, null, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        ElevatorStatus elevatorStatus2 = new ElevatorStatus(1, null, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        List<Elevator> elevators = new LinkedList<>(List.of(new Elevator(2, elevatorStatus1), new Elevator(233, elevatorStatus2)));

        //when
        elevatorOptimizationService.configureEmptyElevators(elevators, 8);

        //then
        assertEquals(0, elevators.get(0).getElevatorStatus().getCurrentFloor());
        assertEquals(2, elevators.get(1).getElevatorStatus().getCurrentFloor());
    }
}
