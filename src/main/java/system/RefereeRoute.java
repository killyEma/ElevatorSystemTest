package system;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class RefereeRoute implements Runnable {
    private final ObserverCalls _observerCalls;
    private final ElevatorSystem _elevatorSystem;
    private Thread _timerToCloseDoor;
    private final int _countTimerSeconds = getSecondsFromPropertyConfig();

    private AtomicBoolean timeEnd;

    public RefereeRoute(ObserverCalls observerCalls, ElevatorSystem elevatorSystem) {
        _elevatorSystem = elevatorSystem;
        _observerCalls = observerCalls;
    }

    @Override
    public void run() {
        while (true) {
            waitToAsk();
            startElevatorToDestinationsEntered();
            startElevatorToDestinationCalled();
        }
    }

    private void startElevatorToDestinationCalled() {
        if (_observerCalls.hasCalls() && !_elevatorSystem.started() && theTimeIsOver()) {
            _timerToCloseDoor = null;
            timeEnd = null;
            _elevatorSystem.startMovingCall(_observerCalls.getNextCall());
            _observerCalls.deleteFirstCall();
        }
    }

    private void startElevatorToDestinationsEntered() {
        if (_elevatorSystem.validToStart()) {
            _elevatorSystem.startMoving();
        }
    }

    private void waitToAsk() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean theTimeIsOver() {
        if (_timerToCloseDoor == null ) {
            _timerToCloseDoor = new Thread(() -> {
                for (int i = 0; i < _countTimerSeconds; i++) {
                    System.out.println("arranco el tiempo del TIMEEND");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timeEnd = new AtomicBoolean(true);
                System.out.println("se termino el tiempo para esperar el TIMEEND");
            });
            _timerToCloseDoor.start();
        }
        return timeEnd != null && timeEnd.get();
    }

    private int getSecondsFromPropertyConfig() {
        return 5;//TODO: this should be read from a propertyconfig.xml. pipeline should has the seconds
    }
}
