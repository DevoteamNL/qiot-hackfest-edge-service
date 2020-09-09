package nl.devoteam.registration;

import nl.devoteam.registration.model.GeoPosition;

public interface LocationService {

    /**
     * Obtain the geo position of the device at current.
     * @return
     */
    GeoPosition obtainPosition();
}
