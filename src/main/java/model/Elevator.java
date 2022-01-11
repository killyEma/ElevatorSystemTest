package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Elevator extends Observable {
    private final Integer weightLimitKilogram;
    private Boolean alarmOn;
    private Boolean shutOff;
    private Integer actualFloor;
    private List <Integer> memoryDestinationFloors;
    private Double weightInputKilogram;
    private Boolean isDoorOpen;
    private final List <Integer> floorsUsersCalled;

    public Elevator(Integer weightLimitKilogram) {
        this.weightLimitKilogram = weightLimitKilogram;
        this.memoryDestinationFloors = new ArrayList<>();
        this.alarmOn = false;
        this.actualFloor = 0;
        this.shutOff = false;
        this.isDoorOpen = true;
        this.floorsUsersCalled = new ArrayList<>();
    }

    public Integer getWeightLimitKilogram() {
        return weightLimitKilogram;
    }

    public void setAlarmOn(Boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    public void setShutOff(Boolean shutOff) {
        this.shutOff = shutOff;
    }

    public Integer getActualFloor() {
        return actualFloor;
    }

    public void setActualFloor(Integer actualFloor) {
        this.actualFloor = actualFloor;
    }

    public List<Integer> getMemoryDestinationFloors() {
        return memoryDestinationFloors;
    }

    public void setMemoryDestinationFloors(List<Integer> memoryDestinationFloors) {
        this.memoryDestinationFloors = memoryDestinationFloors;
    }

    public void setWeightInputKilogram(Double weightInputKilogram) {
        this.weightInputKilogram = weightInputKilogram;
    }

    public Double getWeightInputKilogram() {
        return weightInputKilogram;
    }

    public Boolean getDoorOpen() {
        return isDoorOpen;
    }

    public void setDoorOpen(Boolean doorOpen) {
        if (isDoorOpen.equals(doorOpen)) {
            return;
        }
        if (doorOpen) System.out.println("(se abre la puerta)");
        if (!doorOpen) System.out.println("(se cierra la puerta para seguir destinos)");
        isDoorOpen = doorOpen;
        if(isDoorOpen) return;

    }

    public int getNexFloorTarget() {
        return this.memoryDestinationFloors.get(0);
    }

    public List<Integer> getFloorsUsersCalled() {
        return floorsUsersCalled;
    }

}
