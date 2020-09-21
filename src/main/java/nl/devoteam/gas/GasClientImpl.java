package nl.devoteam.gas;

import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class GasClientImpl implements GasClient {

    private static final Logger LOGGER = Logger.getLogger(GasClientImpl.class.getName());
    private final GasResource gasResource;
    @Inject
    @Channel("gas")
    Emitter<String> gasEmitter;

    public GasClientImpl(@RestClient GasResource gasResource) {
        this.gasResource = gasResource;
    }

    @Override
    public void postGasData(GasData data) {
        Jsonb jsonb = JsonbBuilder.create();
        final String payload = jsonb.toJson(data);
        LOGGER.info("Sending payload to Pollution Channel: " + payload);
        gasEmitter.send(payload);
    }

    @Override
    public GasData receiveGasData(int stationId) {
        return new GasData(stationId, new Date().toInstant().toString(), 1.0, 2.0, 3.0, 4.0);
//        return gasResource.getGasData();
    }
}
