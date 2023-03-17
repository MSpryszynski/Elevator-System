package com.example.avsystem.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorPickupServiceTest {

    private final ElevatorPickupService elevatorPickupService = new ElevatorPickupService();

    @Test
    public void pickupEmptyElevator() {
        //given
        Elevator elevator = new Elevator(123);
        PickupRequest pickupRequest = new PickupRequest(3, ElevatorDirection.UP);

        //when
        elevatorPickupService.pickupElevator(elevator, pickupRequest);

        //then
        assertEquals(Optional.of(3), elevator.getElevatorStatus().getPickupFloor());
        assertEquals(ElevatorDirection.UP, elevator.getElevatorStatus().getPickedDirection());
    }

    @Test
    public void pickupElevatorWithTargetFloor() {
        //given
        ElevatorStatus elevatorStatus = new ElevatorStatus(2, null, new PriorityQueue<>(List.of(6)), new PriorityQueue<>(), ElevatorDirection.UP, false, false);
        Elevator elevator = new Elevator(123, elevatorStatus);
        PickupRequest pickupRequest = new PickupRequest(4, ElevatorDirection.UP);

        //when
        elevatorPickupService.pickupElevator(elevator, pickupRequest);

        //then
        assertEquals(Optional.empty(), elevator.getElevatorStatus().getPickupFloor());
        assertEquals(Optional.of(4), elevator.getElevatorStatus().getExtraPickupFloor());
    }

    @Test
    public void pickupElevatorWithPickupFloor() {
        //given
        ElevatorStatus elevatorStatus = new ElevatorStatus(2, 3, new PriorityQueue<>(List.of(6)), new PriorityQueue<>(), ElevatorDirection.UP, false, false);
        Elevator elevator = new Elevator(123, elevatorStatus);
        PickupRequest pickupRequest = new PickupRequest(4, ElevatorDirection.UP);

        //when
        elevatorPickupService.pickupElevator(elevator, pickupRequest);

        //then
        assertEquals(Optional.of(3), elevator.getElevatorStatus().getPickupFloor());
        assertEquals(Optional.of(4), elevator.getElevatorStatus().getExtraPickupFloor());
    }

    @Test
    public void xd() {
        //given
        ElevatorStatus elevatorStatus1 = new ElevatorStatus(0, null, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        ElevatorStatus elevatorStatus2 = new ElevatorStatus(4, null, new PriorityQueue<>(), new PriorityQueue<>(), null, false, false);
        List<Elevator> elevators = new LinkedList<>(List.of(new Elevator(2, elevatorStatus1), new Elevator(233, elevatorStatus2)));


        //when
        elevatorPickupService.findNearestPossibleElevator(elevators, )

        //then
    }
}
