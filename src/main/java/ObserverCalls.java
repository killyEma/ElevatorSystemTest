import java.util.*;
import java.util.concurrent.TimeUnit;

public class ObserverCalls implements Runnable {
    private List<Integer> floorsUsersCalled;
    private boolean alwaysListenNewDestinations = true;

    public ObserverCalls() {
        floorsUsersCalled = new ArrayList<>();
    }

    @Override
    public void run() {

            int callsCount = 3;
            int timerCalls = 30;
        // sleep 2 seconds
            while (alwaysListenNewDestinations) {
                if(callsCount == 0) {
                    System.out.println("termino solicitudes de piso");
                    return;
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (timerCalls == 7  || timerCalls == 14 || timerCalls == 1) {
                    Integer randomFlor = new Random().nextInt(51 - (-1) + 1) + (-1);
                    System.out.println("piso solicitado " + randomFlor);
                    floorsUsersCalled.add(randomFlor);
                    callsCount = callsCount - 1;

                }
                timerCalls = timerCalls - 1;
            }


    }

    public boolean hasCalls() {
        return !floorsUsersCalled.isEmpty();
    }

    public Integer getNextCall() {
        return floorsUsersCalled.get(0);
    }

    public void deleteFirstCall() {
        floorsUsersCalled.remove(0);
    }
}
