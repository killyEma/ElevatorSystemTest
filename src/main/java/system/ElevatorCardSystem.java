package system;

import delegate.ElevatorDelegator;
import external.service.SecurityCardService;


public class ElevatorCardSystem extends ElevatorSystem {
    private SecurityCardService _securityCardService;

    public ElevatorCardSystem(ElevatorDelegator elevatorDelegator) {
        super(elevatorDelegator);
    }


    public ElevatorCardSystem(ElevatorDelegator elevatorDelegator, SecurityCardService securityCardService) {
        super(elevatorDelegator);
        _securityCardService = securityCardService;
    }

//    @Override
//    public void run() {
//        while (true) {
//            waitToAsk();
//
//            if (!_ElevatorDelegator.isDoorOpen() ||
//                _ElevatorDelegator.elevatorHasDestinationFloors()) {
//                continue;
//            }
//
//            initActualFloor();
//            elevatorReadsTheFloorsThatAreEnteredByUsers();
//            elevatorValidatesTheWeightEntered();
//
//            _ElevatorDelegator.closeDoor();
//        }
//    }

    @Override
    protected void elevatorReadsTheFloorsThatAreEnteredByUsers() {
        int  floorEntered = -10;
        while(!_ElevatorDelegator.isValidToCloseDoor()) {
            floorEntered = retrieveNewFloorInput();

            if(floorSelectedHasSecurityCard(floorEntered) &&
               isValidatedSecurityCard(scannerSecurityCard())) {
                System.out.println("tarjeta inválida, se omite piso");
                continue;
            }

            _ElevatorDelegator.addInputKeyboardOption(floorEntered);
        }
    }

    private boolean isValidatedSecurityCard(boolean scannerSecurityCard) {
        return _securityCardService.validSecurityCard(scannerSecurityCard);
    }

    private boolean floorSelectedHasSecurityCard(int floorEntered) {
        return _securityCardService.validFloor(floorEntered);
    }

    private boolean scannerSecurityCard() {
        return false;
    }

}
