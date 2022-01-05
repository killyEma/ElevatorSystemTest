import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AppElevator {

    public static void main(String[] args) {
        Elevator freightElevator = new FreightElevator(3000);
        int floors = 52;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer newFloorDestination = -99;
        Double weightInput = Double.NaN;

        // mirar si la lista de llamados tiene algo

        FreightElevatorDelegator freightElevatorDelegator = new FreightElevatorDelegator(freightElevator);
        freightElevator.addObserver(freightElevatorDelegator);
        startObserverFloorCalls(freightElevatorDelegator);

        // add floor
        while(true) {
            freightElevatorDelegator.clearActualFloor();
            while(!freightElevatorDelegator.isValidToCloseDoor()) {
                System.out.println("Ingrese los pisos antes que se cierre la puerta");
                try {
                    newFloorDestination = Integer.parseInt(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                freightElevatorDelegator.addInputKeyboardOption(newFloorDestination);
            }
    // END add floor
                                //elevador público: pide primero validar la keycard
                                // si es el basement o el 50 piso se pide la tarjeta
                                // lector roto ingrese pase tarjeta
                                // si NO es el basement o el 50 piso se pide la tarjeta

    // elevador valida si el peso es el correcto, se ingresa el peso
            try {
                System.out.println("El peso total es: ");
                weightInput = Double.parseDouble(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            freightElevatorDelegator.setWeightInput(weightInput);
            while(!freightElevatorDelegator.isValidWeightInput()) {
                freightElevatorDelegator.startWeightAlarm();
                System.out.println("Error: weight was exceeded... enter new own");
                try {
                    weightInput = Double.parseDouble(reader.readLine());
                    freightElevatorDelegator.setWeightInput(weightInput);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            freightElevatorDelegator.weightIsValidated();
            freightElevatorDelegator.sortArrivalFloors();
            freightElevatorDelegator.closeDoor();
    // FIN PESO

    //elevador disminuye o asciende de a uno hasta llegar hasta el piso seleccionado
            //freightElevatorDelegator.elevatorStartRoute();
        }
    }

    private static void startObserverFloorCalls(FreightElevatorDelegator freightElevator) {

        Timer timerCalls = new Timer();

        TimerTask task = new TimerTask() {
            private int callsCount = 3;
            private int timerCalls = 30;
            public void run(){

                if(callsCount == 0) {
                    System.out.println("termino solicitudes de piso");
                    this.cancel();
                }


                if (timerCalls == 7  || timerCalls == 14 || timerCalls == 1) {
                    Integer randomFlor = new Random().nextInt(51 - (-1) + 1) + (-1);
                    System.out.println("piso solicitado " + randomFlor);
                    freightElevator.addDestinationFloorCalled(randomFlor);
                    callsCount = callsCount - 1;
                    freightElevator.setWeightInput(600.0);
                    freightElevator.weightIsValidated();
                    // hacer otro time para cerrar la puerta
                    if(freightElevator.isValidToCloseDoorForCall()) {
                        freightElevator.closeDoor();
                    }

                }
                timerCalls = timerCalls - 1;
            }

        };
        timerCalls.scheduleAtFixedRate(task, 0, 1000);
    }


}
