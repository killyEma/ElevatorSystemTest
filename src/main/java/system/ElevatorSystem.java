package system;

import delegate.ElevatorDelegator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class ElevatorSystem implements Runnable {
    protected ElevatorDelegator _ElevatorDelegator;
    private BufferedReader reader;
    private final static int FLOORS = getFloorFromPropertyConfig();
    private Integer newFloorDestination;
    private Double weightInput;

    public ElevatorSystem(ElevatorDelegator elevatorDelegator) {
        _ElevatorDelegator = elevatorDelegator;
        reader = new BufferedReader(new InputStreamReader(System.in));
        newFloorDestination = -99;
        weightInput = Double.NaN;
    }

    @Override
    public void run() {
        while (true ) {
            waitToAsk();

            if (!_ElevatorDelegator.isDoorOpen() ||
                _ElevatorDelegator.elevatorHasDestinationFloors()) {
                continue;
            }

            initActualFloor();
            elevatorReadsTheFloorsThatAreEnteredByUsers();
            elevatorValidatesTheWeightEntered();

            _ElevatorDelegator.closeDoor();
        }

    }

    protected void initActualFloor() {
        _ElevatorDelegator.clearActualFloor();
    }

    protected void elevatorValidatesTheWeightEntered() {
        try {
            System.out.println("El peso total es: ");
            weightInput = Double.parseDouble(reader.readLine());
            _ElevatorDelegator.setWeightInput(weightInput);
            while(!_ElevatorDelegator.isValidWeightInput()) {
                _ElevatorDelegator.startWeightAlarm();
                System.out.println("Error: weight was exceeded... enter new own");
                weightInput = Double.parseDouble(reader.readLine());
                _ElevatorDelegator.setWeightInput(weightInput);

            }
            _ElevatorDelegator.weightIsValidated();
            _ElevatorDelegator.sortArrivalFloors();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void elevatorReadsTheFloorsThatAreEnteredByUsers() {
        while(!_ElevatorDelegator.isValidToCloseDoor()) {
            _ElevatorDelegator.addInputKeyboardOption(retrieveNewFloorInput());
        }
    }

    protected Integer retrieveNewFloorInput() {
        System.out.println("Ingrese los pisos antes que se cierre la puerta");
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Fatal Error");
            e.printStackTrace();
            return null;
        }
    }

    public void startMoving() {
        _ElevatorDelegator.elevatorStartRoute();
    }


    public void startMovingCall(Integer targetCallFloor) {
        _ElevatorDelegator.addDestinationFloorCalled(targetCallFloor);
        _ElevatorDelegator.elevatorStartRouteCall();
    }

    public boolean validToStart() {
        return !_ElevatorDelegator.isDoorOpen() &&
                _ElevatorDelegator.isValidWeightInput() &&
                _ElevatorDelegator.isValidToCloseDoor();

    }

    public boolean started() {
        return _ElevatorDelegator.elevatorHasDestinationFloors();
    }

    private static int getFloorFromPropertyConfig() {
        return 52; //TODO: this should be read from a propertyconfig.xml. pipeline should has a floor count
    }

    protected void waitToAsk() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
