package nl.devoteam.registration;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RegistrationClientImpl implements RegistrationClient {

    @Override
    public int registerDevice(double latitude, double longitude, String name, String serial) {
        return 0;
    }

    @Override
    public void deregisterDevice(int id) {

    }
}
