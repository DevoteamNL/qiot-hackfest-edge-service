package nl.devoteam.registration;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@Startup
@ApplicationScoped
public class ApplicationLifecycleListener {

    private static final Logger LOGGER = Logger.getLogger(ApplicationLifecycleListener.class.getName());
    private final RegistrationClient registrationClient;
    private final LocationService locationService;
    private final DeviceMetadataService deviceMetadataService;

    public ApplicationLifecycleListener(RegistrationClient registrationClient,
        LocationService locationService, DeviceMetadataService deviceMetadataService) {
        this.registrationClient = registrationClient;
        this.locationService = locationService;
        this.deviceMetadataService = deviceMetadataService;
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting, registering device.");
        GeoPosition geoPosition = locationService.obtainPosition();
        int registrationId = registrationClient
            .registerDevice(geoPosition.getLatitude(), geoPosition.getLongitude(), "teamNoIdea", "serial");
        deviceMetadataService.setRegistrationId(registrationId);
        LOGGER.info("Registered this device with ID: " + registrationId);
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping, deregistering device.");
        int registrationId = deviceMetadataService.getRegistrationId();
        registrationClient.deregisterDevice(registrationId);
    }
}
