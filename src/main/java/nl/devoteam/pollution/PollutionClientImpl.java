package nl.devoteam.pollution;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logmanager.Level;

@ApplicationScoped
public class PollutionClientImpl implements PollutionClient {

    private static final Logger LOGGER = Logger.getLogger(PollutionClientImpl.class.getName());
    private final PollutionResource pollutionResource;

    @Inject
    @Channel("pollution")
    Emitter<String> pollutionEmitter;

    public PollutionClientImpl(@RestClient PollutionResource pollutionResource) {
        this.pollutionResource = pollutionResource;
    }

    @Override
    public void postPollutionData(PollutionData data) {
        Jsonb jsonb = JsonbBuilder.create();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("stationId", data.getStationId());
        map.put("instant", data.getInstant());
        map.put("PM1_0", data.getPm1_0());
        map.put("PM2_5", data.getPm2_5());
        map.put("PM10", data.getPm10());
        map.put("PM1_0_atm", data.getPm1_0_atm());
        map.put("PM2_5_atm", data.getPm2_5_atm());
        map.put("PM10_atm", data.getPm10_atm());
        map.put("gt0_3um", data.getGt0_3um());
        map.put("gt0_5um", data.getGt0_5um());
        map.put("gt1_0um", data.getGt1_0um());
        map.put("gt2_5um", data.getGt2_5um());
        map.put("gt5_0um", data.getGt5_0um());
        map.put("gt10um", data.getGt10um());
        final String payload = jsonb.toJson(map);

        LOGGER.info("Sending payload to Pollution Channel: " + payload);
        CompletionStage<Void> send = pollutionEmitter.send(payload);
        send.whenComplete((unused, throwable) -> LOGGER.info("Managed to send data."))
            .exceptionally(throwable -> {
                LOGGER.log(Level.ERROR, "Failed to send message. Error: " + throwable.getMessage());
                return null;
            });
    }

    @Override
    public PollutionData receivePollutionData() {
        return pollutionResource.getPollutionData();
    }
}
