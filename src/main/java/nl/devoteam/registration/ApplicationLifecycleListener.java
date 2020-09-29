package nl.devoteam.registration;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@ApplicationScoped
public class ApplicationLifecycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLifecycleListener.class.getName());
    private final ManagementServiceResource managementServiceResource;
    private final RegistrationClient registrationClient;
    private final LocationService locationService;
    private final DeviceMetadataService deviceMetadataService;

    public ApplicationLifecycleListener(@RestClient ManagementServiceResource managementServiceResource,
        RegistrationClient registrationClient, LocationService locationService,
        DeviceMetadataService deviceMetadataService) {
        this.managementServiceResource = managementServiceResource;
        this.registrationClient = registrationClient;
        this.locationService = locationService;
        this.deviceMetadataService = deviceMetadataService;
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting, registering device.");
        GeoPosition geoPosition = locationService.obtainPosition();
        final String serial = managementServiceResource.getSerial();
        int registrationId = registrationClient
            .registerDevice(geoPosition.getLatitude(), geoPosition.getLongitude(), "IcanHazIOT", serial);
        deviceMetadataService.setRegistrationId(registrationId);
        LOGGER.info("Registered this device with ID: " + registrationId);
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping, deregistering device.");
        int registrationId = deviceMetadataService.getRegistrationId();
        registrationClient.deregisterDevice(registrationId);
    }
}
