package nl.devoteam.gas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GasClientImpl implements GasClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GasClientImpl.class.getName());
    private final GasResource gasResource;
    private final Emitter<String> gasEmitter;

    public GasClientImpl(@RestClient GasResource gasResource,
        @Channel("gas") Emitter<String> gasEmitter) {
        this.gasResource = gasResource;
        this.gasEmitter = gasEmitter;
    }

    @Override
    public void postGasData(GasData data) {
        Jsonb jsonb = JsonbBuilder.create();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("stationId", data.getStationId());
        map.put("instant", data.getInstant());
        map.put("adc", data.getAdc());
        map.put("nh3", data.getNh3());
        map.put("oxidising", data.getOxidising());
        map.put("reducing", data.getReducing());
        final String payload = jsonb.toJson(map);
        LOGGER.info("Sending payload to Gas Channel: " + payload);
        CompletionStage<Void> send = gasEmitter.send(payload);
        send.whenComplete((unused, throwable) -> LOGGER.info("Managed to send gas data."))
            .exceptionally(throwable -> {
                LOGGER.error("Failed to send message. Error: " + throwable.getMessage());
                return null;
            });
    }

    @Override
    public GasData receiveGasData() {
        return gasResource.getGasData();
    }
}
