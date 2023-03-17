package com.example.avsystem.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ElevatorSimulationServiceTest {


    private final ElevatorSimulationService elevatorSimulationService = new ElevatorSimulationService();

    @Test
    public void whileOnPickupFloorWait() {
        //given
        Elevator elevator = mock(Elevator.class);
        ElevatorStatus elevatorStatus = new ElevatorStatus(4, 6, new PriorityQueue<>(), new PriorityQueue<>(), null, true, false);
        when(elevator.getElevatorStatus()).thenReturn(elevatorStatus);

        //when
        elevatorSimulationService.simulateElevatorStep(elevator);

        //then
        assertEquals(4, elevatorStatus.getCurrentFloor());
        assertFalse(elevatorStatus.isOnPickupFloor());
    }

    @Test
    public void whileOnTargetFloorWait() {
        //given
        Elevator elevator = mock(Elevator.class);
        ElevatorStatus elevatorStatus = new ElevatorStatus(4, 6, new PriorityQueue<>(), new PriorityQueue<>(), null, false, true);
        when(elevator.getElevatorStatus()).thenReturn(elevatorStatus);

        //when
        elevatorSimulationService.simulateElevatorStep(elevator);

        //then
        assertEquals(4, elevatorStatus.getCurrentFloor());
        assertFalse(elevatorStatus.isOnPickupFloor());
    }

    @Test
    public void moveInPickupFloorDirection() {
        //given
        Elevator elevator = mock(Elevator.class);
        ElevatorStatus elevatorStatus = new ElevatorStatus(4, 6, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        when(elevator.getElevatorStatus()).thenReturn(elevatorStatus);

        //when
        elevatorSimulationService.simulateElevatorStep(elevator);

        //then
        assertEquals(5, elevatorStatus.getCurrentFloor());
        assertFalse(elevatorStatus.isOnPickupFloor());
    }

    @Test
    public void arriveToPickupFloor() {
        //given
        Elevator elevator = mock(Elevator.class);
        ElevatorStatus elevatorStatus = new ElevatorStatus(5, 5, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        when(elevator.getElevatorStatus()).thenReturn(elevatorStatus);

        //when
        elevatorSimulationService.simulateElevatorStep(elevator);

        //then
        assertEquals(5, elevatorStatus.getCurrentFloor());
        assertEquals(Optional.empty(), elevatorStatus.getPickupFloor());
        assertTrue(elevatorStatus.isOnPickupFloor());
    }

    @Test
    public void arriveToExtraPickupFloor() {
        //given
        Elevator elevator = mock(Elevator.class);
        ElevatorStatus elevatorStatus = new ElevatorStatus(5, 7, new PriorityQueue<>(), new PriorityQueue<>(List.of(5, 8)), null, false, false);
        when(elevator.getElevatorStatus()).thenReturn(elevatorStatus);

        //when
        elevatorSimulationService.simulateElevatorStep(elevator);

        //then
        assertEquals(5, elevatorStatus.getCurrentFloor());
        assertEquals(Optional.of(7), elevatorStatus.getPickupFloor());
        assertTrue(elevatorStatus.isOnPickupFloor());
        assertEquals(Optional.of(8), elevatorStatus.getExtraPickupFloor());
    }

    @Test
    public void arriveToTargetFloor() {
        //given
        Elevator elevator = mock(Elevator.class);
        ElevatorStatus elevatorStatus = new ElevatorStatus(5, 7, new PriorityQueue<>(List.of(5, 8)), new PriorityQueue<>(), null, false, false);
        when(elevator.getElevatorStatus()).thenReturn(elevatorStatus);

        //when
        elevatorSimulationService.simulateElevatorStep(elevator);

        //then
        assertEquals(5, elevatorStatus.getCurrentFloor());
        assertEquals(Optional.of(7), elevatorStatus.getPickupFloor());
        assertTrue(elevatorStatus.isOnTargetFloor());
        assertEquals(Optional.of(8), elevatorStatus.getTargetFloor());
    }
}
