package nl.devoteam.telemetry;


import io.quarkus.scheduler.Scheduled;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import nl.devoteam.gas.GasClient;
import nl.devoteam.gas.GasData;
import nl.devoteam.pollution.PollutionClient;
import nl.devoteam.pollution.PollutionData;
import nl.devoteam.registration.DeviceMetadataService;

@ApplicationScoped
public class TelemetryService {

    private static final Logger LOGGER = Logger.getLogger(TelemetryService.class.getName());
    private final GasClient gasClient;
    private final PollutionClient pollutionClient;
    private final DeviceMetadataService deviceMetadataService;

    public TelemetryService(GasClient gasClient, PollutionClient pollutionClient,
        DeviceMetadataService deviceMetadataService) {
        this.gasClient = gasClient;
        this.pollutionClient = pollutionClient;
        this.deviceMetadataService = deviceMetadataService;
    }

    @Scheduled(every = "5s")
    void sendPollutionData() {
        long startTime = new Date().getTime();
        LOGGER.info("Gathering Pollution Data -- Starting scheduled method at " + startTime);

        PollutionData pollutionData = pollutionClient.receivePollutionData(deviceMetadataService.getRegistrationId());
        pollutionClient.postPollutionData(pollutionData);

        long endTime = new Date().getTime();
        LOGGER.info("Gathering Pollution Data -- Duration: " + (endTime - startTime));
    }

    @Scheduled(every = "5s")
    void sendGasData() {
        long startTime = new Date().getTime();
        LOGGER.info("Gathering Gas Data -- Starting scheduled method at " + startTime);

        GasData gasData = gasClient.receiveGasData(deviceMetadataService.getRegistrationId());
        gasClient.postGasData(gasData);

        long endTime = new Date().getTime();
        LOGGER.info("Gathering Gas Data -- Duration: " + (endTime - startTime));
    }
}
