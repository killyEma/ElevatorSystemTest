import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Elevator extends Observable {
    private Integer weightLimitKilogram;
    private Boolean alarmOn;
    private Boolean shutOff;
    private Boolean isDoorSuck;
    private Integer actualFloor;
    private Integer targetFloor;
    private List <Integer> memoryDestinationFloors;
    private Double weightInputKilogram;
    private Integer timeOpenDoor;
    private Boolean isDoorOpen;
    private List <Integer> floorsUsersCalled;
    private Boolean isMoving;

    public Elevator(Integer weightLimitKilogram) {
        this.weightLimitKilogram = weightLimitKilogram;
        this.memoryDestinationFloors = new ArrayList<>();
        this.isDoorSuck = false;
        this.alarmOn = false;
        this.actualFloor = 0;
        this.shutOff = false;
        this.timeOpenDoor = 15;
        this.isDoorOpen = true;
        this.floorsUsersCalled = new ArrayList<>();
        this.isMoving = false;
    }

    public Integer getWeightLimitKilogram() {
        return weightLimitKilogram;
    }

    public void setWeightLimitKilogram(Integer weightLimitKilogram) {
        this.weightLimitKilogram = weightLimitKilogram;
    }

    public Boolean getAlarmOn() {
        return alarmOn;
    }

    public void setAlarmOn(Boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    public Boolean getShutOff() {
        return shutOff;
    }

    public void setShutOff(Boolean shutOff) {
        this.shutOff = shutOff;
    }

    public Boolean getDoorSuck() {
        return isDoorSuck;
    }

    public void setDoorSuck(Boolean doorSuck) {
        isDoorSuck = doorSuck;
    }

    public Integer getActualFloor() {
        return actualFloor;
    }

    public void setActualFloor(Integer actualFloor) {
        this.actualFloor = actualFloor;
    }

    public Integer getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(Integer targetFloor) {
        this.targetFloor = targetFloor;
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

    public Integer getTimeOpenDoor() {
        return timeOpenDoor;
    }

    public void setTimeOpenDoor(Integer timeOpenDoor) {
        this.timeOpenDoor = timeOpenDoor;
    }

    public Boolean getDoorOpen() {
        return isDoorOpen;
    }

    public void setDoorOpen(Boolean doorOpen) {
        isDoorOpen = doorOpen;
        setChanged();
        notifyObservers();
    }

    public int getNexFloorTarget() {
        return this.memoryDestinationFloors.get(0);
    }

    public List<Integer> getFloorsUsersCalled() {
        return floorsUsersCalled;
    }

    public void setFloorsUsersCalled(List<Integer> floorsUsersCalled) {
        this.floorsUsersCalled = floorsUsersCalled;
    }

    public Boolean getMoving() {
        return isMoving;
    }

    public void setMoving(Boolean moving) {
        isMoving = moving;
    }
}
