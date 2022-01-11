public class AppElevatorCard {
    public static void main(String[] args) {
        Elevator freightElevator = new FreightElevator(getWeightLimitKilogramFromPropertyConfig());

        ElevatorDelegator elevatorDelegator = new ElevatorDelegator(freightElevator);

        ObserverCalls observerCalls = new ObserverCalls();
        ElevatorSystem elevatorSystem = new ElevatorSystem(elevatorDelegator);
        RefereeRoute refereeRoute = new RefereeRoute(observerCalls, elevatorSystem);

        Thread ObserverCallsThread = new Thread(observerCalls);
        Thread elevatorThread = new Thread(elevatorSystem);
        Thread RefereeRouteThread = new Thread(refereeRoute);

        ObserverCallsThread.start();
        elevatorThread.start();
        RefereeRouteThread.start();

    }

    private static int getWeightLimitKilogramFromPropertyConfig() {
        return 3000;//TODO: this should be read from a propertyconfig.xml. pipeline should has the weight
    }
}
