import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FreightElevatorDelegator implements Observer {
    public static final int FLOOR_UP = 1;
    public static final int FLOOR_DOWN = -1;
    private Elevator freightElevator;
    private Boolean flagClosingDoor;
    //private Timer timerCloseDoor;

    public FreightElevatorDelegator(Elevator freightElevator) {
        this.freightElevator = freightElevator;
    }

    public void clearActualFloor() {
        if(!freightElevator.getMemoryDestinationFloors().isEmpty()) {
            freightElevator.setActualFloor(freightElevator.getMemoryDestinationFloors().get(0));
            freightElevator.getMemoryDestinationFloors().remove(0);
        }
        initOpenDoorFlow();
    }

    private void initOpenDoorFlow() {
        flagClosingDoor = false;
        freightElevator.setDoorOpen(true);
        freightElevator.setMoving(false);
        //timerCloseDoor = initTimerToCloseOpenDoor();
    }



    public void addDestinationFloor(Integer newFloorDestination) {
        if(newFloorDestination.equals(freightElevator.getActualFloor())) {
           return;
        }

        List<Integer> destinations = freightElevator.getMemoryDestinationFloors();
        if (!destinations.contains(newFloorDestination)) {
            freightElevator.getMemoryDestinationFloors().add(newFloorDestination);
        }
    }

    public boolean elevatorHasDestinationFloors() {
        return !freightElevator.getMemoryDestinationFloors().isEmpty();
    }

    public void setWeightInput(Double weightInput) {
        freightElevator.setWeightInputKilogram(weightInput);
    }

    public boolean isValidWeightInput() {
        return freightElevator.getWeightInputKilogram() <= freightElevator.getWeightLimitKilogram();
    }

    public void startWeightAlarm() {
        this.freightElevator.setAlarmOn(true);
        this.freightElevator.setShutOff(true);

    }

    public void weightIsValidated() {
        this.freightElevator.setAlarmOn(false);
        this.freightElevator.setShutOff(false);
    }

    public boolean isValidToCloseDoor() {
        return flagClosingDoor && elevatorHasDestinationFloors();
    }

    public void closeDoor() {
        freightElevator.setDoorOpen(false);
    }

    private void elevatorStartRoute() {
        while (hasDestinationFloors()) {
            freightElevator.setMoving(true);
            int actualFloor = freightElevator.getActualFloor();
            int target = freightElevator.getNexFloorTarget();
            elevatorMovingToTargetFloor(actualFloor, target);
            freightElevator.setActualFloor(target);
            deleteTargetFromDestinationRoute(target);

            floorIsReached();



        }
        freightElevator.setDoorOpen(true);
        System.out.println("termino recorrido");
    }

    private void floorIsReached() {
        freightElevator.setDoorOpen(true);
        System.out.println("(se abre puerta para descenso)");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("(se cierra la puerta para seguir destinos)");
        freightElevator.setDoorOpen(true);
    }

    private boolean hasDestinationFloors() {
        return !this.freightElevator.getMemoryDestinationFloors().isEmpty();
    }

    private boolean hasDestinationFloorsCall() {
        return !this.freightElevator.getFloorsUsersCalled().isEmpty();
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
        List<Integer> filteredDestinationFloors = freightElevator.getMemoryDestinationFloors()
                                                                .stream()
                                                                .filter(floor -> !floor.equals(floorToDelete) )
                                                                .collect(Collectors.toList());
        freightElevator.setMemoryDestinationFloors(filteredDestinationFloors);
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
        //addDestinationFloor(randomFlor);
        addDestinationCallFloor(randomFlor);
        //this.flagClosingDoor = true;
    }

    public void addDestinationCallFloor(Integer newFloorDestination) {
        if(!newFloorDestination.equals(freightElevator.getActualFloor())) {
            List<Integer> destinations = freightElevator.getFloorsUsersCalled();
            if (!destinations.contains(newFloorDestination)) {
                freightElevator.getFloorsUsersCalled().add(newFloorDestination);
            }
        }
    }

    @Override
    public void update(Observable observableElevator, Object arg) {

        // arranca detenido y se sube y pulsa para ir a destino
        if (freightElevator == observableElevator &&
            !freightElevator.getDoorOpen() &&
            hasDestinationFloors() &&
            !freightElevator.getMoving()) {
            elevatorStartRoute();
            return;
        }

        // si esta detenido viene por aca
        if (freightElevator == observableElevator &&
            !freightElevator.getMoving() &&
            hasDestinationFloorsCall() ) {

            freightElevator.getMemoryDestinationFloors().addAll(freightElevator.getFloorsUsersCalled());
            freightElevator.getFloorsUsersCalled().remove(0);
            freightElevator.setDoorOpen(false);

            elevatorStartRoute();
            return;
        }

        // cuando ya esta en movimiento varias llamadas
        if (freightElevator == observableElevator &&
                freightElevator.getMoving() &&
                hasDestinationFloorsCall() ) {

            freightElevator.getMemoryDestinationFloors().addAll(freightElevator.getFloorsUsersCalled());
            freightElevator.getFloorsUsersCalled().remove(0);
            freightElevator.setDoorOpen(false);

            elevatorStartRoute();
        }
    }

    public boolean isValidToCloseDoorForCall() {
        return freightElevator.getDoorOpen();
    }

    public void sortArrivalFloors() {
        List<Integer> floors = freightElevator.getMemoryDestinationFloors();
        List<Integer> newFloorsSorted = new ArrayList<>();
        int pivotFloor = freightElevator.getActualFloor();

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
            //System.out.println("siguiente piso" + theNumber);
        }
        freightElevator.setMemoryDestinationFloors(newFloorsSorted);
    }


    /**
     * TODO: flujo para que se cierre la puerta en un determinado tiempo
     *
     */
    private Timer initTimerToCloseOpenDoor() {
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask(){
              private int count = 0;
              public void run() {
                  if(count == freightElevator.getTimeOpenDoor() && hasDestinationFloors()) {
                      //flagClosingDoor = true;
                      t.cancel();
                      t.purge();
                      System.out.println("puerta cerrandose");
                  }

                  if(count == freightElevator.getTimeOpenDoor()) {
                      count = 0;
                  }

                  count = count + 1;
              }

          },
        0,
        1000);
        return t;
    }
}
