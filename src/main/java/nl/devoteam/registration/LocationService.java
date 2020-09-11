package nl.devoteam.registration;

public interface LocationService {

    /**
     * Obtain the geo position of the device at current.
     * @return
     */
    GeoPosition obtainPosition();
}
