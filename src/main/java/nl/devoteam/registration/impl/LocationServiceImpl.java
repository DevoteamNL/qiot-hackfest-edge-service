package nl.devoteam.registration.impl;

import javax.enterprise.context.ApplicationScoped;
import nl.devoteam.registration.model.GeoPosition;
import nl.devoteam.registration.LocationService;

@ApplicationScoped
public class LocationServiceImpl implements LocationService {

    @Override
    public GeoPosition obtainPosition() {
        //TODO: Implement geo location query mechanism.
        return new GeoPosition(0.0, 0.0);
    }
}
