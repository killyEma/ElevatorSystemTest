package external.service;

public interface SecurityCardService {
    boolean validFloor(int floorEntered);

    boolean validSecurityCard(boolean scannerSecurityCard);
}
