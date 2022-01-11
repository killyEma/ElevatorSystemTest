package external.service;

public class SecurityCardServiceMock implements SecurityCardService{
    @Override
    public boolean validFloor(int floorEntered) {
        return false;
    }

    @Override
    public boolean validSecurityCard(boolean scannerSecurityCard) {
        return false;
    }
}
