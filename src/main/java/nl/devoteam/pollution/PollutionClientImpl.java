package nl.devoteam.pollution;

import java.util.Date;
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
        final String payload = jsonb.toJson(data);
        LOGGER.info("Sending payload to Pollution Channel: " + payload);
        CompletionStage<Void> send = pollutionEmitter.send(payload);
        send.whenComplete((unused, throwable) -> {
                LOGGER.info("Managed to send data.");
            })
            .exceptionally(throwable -> {
                LOGGER.log(Level.ERROR, "Failed to send message. Error: " + throwable.getMessage());
                return null;
            });
    }

    @Override
    public PollutionData receivePollutionData(int stationId) {
        return new PollutionData(stationId, new Date().toInstant().toString(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
//        return pollutionResource.getPollutionData();
    }
}
