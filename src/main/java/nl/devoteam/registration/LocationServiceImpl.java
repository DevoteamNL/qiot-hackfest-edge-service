package nl.devoteam.registration;

public class LocationServiceImpl implements LocationService {

    @Override
    public GeoPosition obtainPosition() {
        //TODO: Implement geo location query mechanism.
        return new GeoPosition(0.0, 0.0);
    }
}
