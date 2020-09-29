package nl.devoteam.registration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RegistrationClientImpl implements RegistrationClient {

    @Inject
    @RestClient
    RegistrationResource registrationResource;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationClientImpl.class.getName());

    @ConfigProperty(name = "datahub.api.host")
    String apiHost;

    @ConfigProperty(name = "datahub.api.version")
    String apiVersion;

    @ConfigProperty(name = "datahub.api.registrationPath")
    String registrationPath;

    @Override
    public int registerDevice(double latitude, double longitude, String name, String serial) {
        String returnValue = registrationResource.registerDevice(serial, name, longitude, latitude);
        LOGGER.info("Obtained value from registration endpoint: " + returnValue);
        return Integer.parseInt(returnValue);
    }

    @Override
    public void deregisterDevice(int id) {
        registrationResource.deregisterDevice(id);
    }
}
