package delegate;

import model.Elevator;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ElevatorDelegator {
    public static final int FLOOR_UP = 1;
    public static final int FLOOR_DOWN = -1;
    private Elevator _elevator;
    private Boolean flagClosingDoor;

    public ElevatorDelegator(Elevator elevator) {
        this._elevator = elevator;
    }

    public void clearActualFloor() {
        if(!_elevator.getMemoryDestinationFloors().isEmpty()) {
            _elevator.setActualFloor(_elevator.getMemoryDestinationFloors().get(0));
            _elevator.getMemoryDestinationFloors().remove(0);
        }
        initOpenDoorFlow();
    }

    private void initOpenDoorFlow() {
        flagClosingDoor = false;
    }



    public void addDestinationFloor(Integer newFloorDestination) {
        if(newFloorDestination.equals(_elevator.getActualFloor())) {
           return;
        }

        List<Integer> destinations = _elevator.getMemoryDestinationFloors();
        if (!destinations.contains(newFloorDestination)) {
            _elevator.getMemoryDestinationFloors().add(newFloorDestination);
        }
    }

    public boolean elevatorHasDestinationFloors() {
        return !_elevator.getMemoryDestinationFloors().isEmpty();
    }

    public void setWeightInput(Double weightInput) {
        _elevator.setWeightInputKilogram(weightInput);
    }

    public boolean isValidWeightInput() {
        return _elevator.getWeightInputKilogram() <= _elevator.getWeightLimitKilogram();
    }

    public void startWeightAlarm() {
        this._elevator.setAlarmOn(true);
        this._elevator.setShutOff(true);

    }

    public void weightIsValidated() {
        this._elevator.setAlarmOn(false);
        this._elevator.setShutOff(false);
    }

    public boolean isValidToCloseDoor() {
        return flagClosingDoor && elevatorHasDestinationFloors();
    }

    public void closeDoor() {
        _elevator.setDoorOpen(false);
    }

    public void elevatorStartRoute() {
        while (hasDestinationFloors()) {
            int actualFloor = _elevator.getActualFloor();
            int target = _elevator.getNexFloorTarget();
            elevatorMovingToTargetFloor(actualFloor, target);
            _elevator.setActualFloor(target);
            deleteTargetFromDestinationRoute(target);
            floorIsReached();
        }
        _elevator.setDoorOpen(true);
        System.out.println("termino recorrido");
    }

    private void floorIsReached() {
        _elevator.setDoorOpen(true);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean hasDestinationFloors() {
        return !this._elevator.getMemoryDestinationFloors().isEmpty();
    }

    // esto quizas tengo que ver con logs para enviar
    private void elevatorMovingToTargetFloor(int actualFloor, int target) {
        while (target != actualFloor) {
            if (actualFloor < target) {
                actualFloor = elevatorArrivesAnotherFloor(actualFloor, FLOOR_UP);
            }
            if (actualFloor > target) {
                actualFloor = elevatorArrivesAnotherFloor(actualFloor, FLOOR_DOWN);
            }
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("llego al piso:"+ target);
    }

    private int elevatorArrivesAnotherFloor(int actualFloor, int i) {
        actualFloor = actualFloor + i;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("piso " + actualFloor);

        return actualFloor;
    }

    private void deleteTargetFromDestinationRoute(Integer floorToDelete) {
        List<Integer> filteredDestinationFloors = _elevator.getMemoryDestinationFloors()
                                                                .stream()
                                                                .filter(floor -> !floor.equals(floorToDelete) )
                                                                .collect(Collectors.toList());
        _elevator.setMemoryDestinationFloors(filteredDestinationFloors);
    }

    public void addInputKeyboardOption(Integer newFloorDestination) {
        if (newFloorDestination == 70) {
            flagClosingDoor = true;
            System.out.println("se ingreso opción para cerrar el elevador");
            return;
        }
        if (newFloorDestination >= -1 && newFloorDestination <= 50 ) {
            this.addDestinationFloor(newFloorDestination);
            return;
        }
        System.out.println("error piso inválido ingrese otro ");
    }

    public void addDestinationFloorCalled(Integer randomFlor) {
        addDestinationFloor(randomFlor);
        addDestinationCallFloor(randomFlor);
    }

    public void addDestinationCallFloor(Integer newFloorDestination) {
        if(!newFloorDestination.equals(_elevator.getActualFloor())) {
            List<Integer> destinations = _elevator.getFloorsUsersCalled();
            if (!destinations.contains(newFloorDestination)) {
                _elevator.getFloorsUsersCalled().add(newFloorDestination);
            }
        }
    }


    public void sortArrivalFloors() {
        List<Integer> floors = _elevator.getMemoryDestinationFloors();
        List<Integer> newFloorsSorted = new ArrayList<>();
        int pivotFloor = _elevator.getActualFloor();

        while(!floors.isEmpty()) {
            int distance = Math.abs(floors.get(0) - pivotFloor);
            int newFloor = 0;

            for(int c = 1; c < floors.size(); c++){
                int cdistance = Math.abs(floors.get(c) - pivotFloor);
                if(cdistance < distance){
                    newFloor = c;
                    distance = cdistance;
                }
            }

            int theNumber = floors.get(newFloor);
            pivotFloor = theNumber;
            newFloorsSorted.add(theNumber);
            floors.remove(newFloor);
        }
        _elevator.setMemoryDestinationFloors(newFloorsSorted);
    }

    public boolean isDoorOpen() {
        return _elevator.getDoorOpen();
    }

    public void elevatorStartRouteCall() {
        int actualFloor = _elevator.getActualFloor();
        int target = _elevator.getNexFloorTarget();

        elevatorMovingToTargetFloor(actualFloor, target);
        _elevator.setActualFloor(target);
        deleteTargetFromDestinationRoute(target);

        floorIsReached();

        _elevator.setDoorOpen(true);
        System.out.println("termino recorrido");
    }
}
