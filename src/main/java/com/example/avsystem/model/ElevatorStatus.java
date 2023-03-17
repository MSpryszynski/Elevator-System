package com.example.avsystem.model;

import com.example.avsystem.gui.utils.TargetFloorResponse;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

public class ElevatorStatus {


    private Integer currentFloor = 0;

    private Integer pickupFloor;

    private PriorityQueue<Integer> targetFloors = new PriorityQueue<>();

    private PriorityQueue<Integer> extraPickupFloors = new PriorityQueue<>();

    private ElevatorDirection pickedDirection;

    private boolean onPickupFloor = false;

    private boolean onTargetFloor = false;


    public ElevatorStatus() {
    }

    public ElevatorStatus(Integer currentFloor, Integer pickupFloor, PriorityQueue<Integer> targetFloors,
                          PriorityQueue<Integer> extraPickupFloors, ElevatorDirection pickedDirection,
                          boolean onPickupFloor, boolean onTargetFloor) {
        this.currentFloor = currentFloor;
        this.pickupFloor = pickupFloor;
        this.targetFloors = targetFloors;
        this.extraPickupFloors = extraPickupFloors;
        this.pickedDirection = pickedDirection;
        this.onPickupFloor = onPickupFloor;
        this.onTargetFloor = onTargetFloor;
    }

    public ElevatorDirection getPickedDirection() {
        return pickedDirection;
    }

    public Optional<Integer> getPickupFloor() {
        return Optional.ofNullable(pickupFloor);
    }

    public Optional<Integer> getExtraPickupFloor() {
        return extraPickupFloors.isEmpty() ? Optional.empty() : Optional.of(extraPickupFloors.peek());
    }

    public boolean isOnPickupFloor() {
        return onPickupFloor;
    }

    public void leavePickupFloor() {
        onPickupFloor = false;
    }

    public boolean isOnTargetFloor() {
        return onTargetFloor;
    }

    public void leaveTargetFloor() {
        onTargetFloor = false;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }


    public Optional<Integer> getTargetFloor() {
        return targetFloors.isEmpty() ? Optional.empty() : Optional.of(targetFloors.peek());
    }

    public TargetFloorResponse addTargetFloor(int targetFloor) {
        if (!validTargetDirection(targetFloor)) {
            return TargetFloorResponse.INVALID_DIRECTION;
        }
        if (targetFloors.contains(targetFloor)) {
            return TargetFloorResponse.ALREADY_PICKED;
        }
        targetFloors.add(targetFloor);
        return TargetFloorResponse.SUCCESS;
    }

    public void addExtraPickupFloor(int pickupFloor) {
        if (!extraPickupFloors.contains(pickupFloor)) {
            extraPickupFloors.add(pickupFloor);
        }
    }

    public boolean validTargetDirection(int targetFloor) {
        return (targetFloor - currentFloor) * pickedDirection.toInt() > 0;
    }

    public void pickup(PickupRequest pickupRequest) {
        pickedDirection = pickupRequest.signalizedDirection();
        if (pickedDirection.equals(ElevatorDirection.UP)) {
            targetFloors = new PriorityQueue<>();
            extraPickupFloors = new PriorityQueue<>();
        } else {
            targetFloors = new PriorityQueue<>(Comparator.comparingInt(o -> -o));
            extraPickupFloors = new PriorityQueue<>(Comparator.comparingInt(o -> -o));
        }
        pickupFloor = pickupRequest.pickupFloor();
    }

    public void moveInPickupFloorDirection() {
        boolean arrived = false;
        if (currentFloor.equals(pickupFloor)) {
            pickupFloor = null;
            onPickupFloor = true;
            arrived = true;
        }
        if (!extraPickupFloors.isEmpty() && currentFloor.equals(extraPickupFloors.peek())) {
            onPickupFloor = true;
            extraPickupFloors.remove();
            arrived = true;
        }
        if (!targetFloors.isEmpty() && currentFloor.equals(targetFloors.peek())) {
            joinTargetFloor();
            arrived = true;
        }
        if (!arrived) {
            currentFloor += getDirectionToGivenFloor(pickupFloor).toInt();
        }
    }

    public void moveInTargetDirection() {
        if (!extraPickupFloors.isEmpty() && currentFloor.equals(extraPickupFloors.peek())) {
            onPickupFloor = true;
            extraPickupFloors.remove();
        } else {
            if (currentFloor.equals(targetFloors.peek())) {
                joinTargetFloor();
            } else {
                currentFloor += pickedDirection.toInt();
            }
        }
    }

    private void joinTargetFloor() {
        targetFloors.remove();
        if (targetFloors.isEmpty() && pickupFloor == null) {
            if (!extraPickupFloors.isEmpty()) {
                pickupFloor = extraPickupFloors.poll();
            } else {
                pickedDirection = null;
            }
        }
        onTargetFloor = true;
    }

    public void moveInFloorDirection(int givenFloor) {
        currentFloor += getDirectionToGivenFloor(givenFloor).toInt();
    }

    public boolean isFree() {
        return pickupFloor == null && targetFloors.isEmpty() && !onPickupFloor && !onTargetFloor;
    }

    private ElevatorDirection getDirectionToGivenFloor(int floor) {
        if (floor > currentFloor) {
            return ElevatorDirection.UP;
        } else if (floor < currentFloor) {
            return ElevatorDirection.DOWN;
        }
        return ElevatorDirection.NONE;
    }

}
