package nl.devoteam.pollution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logmanager.ExtHandler;
import org.jboss.logmanager.ExtLogRecord;
import org.jboss.logmanager.Level;
import org.jboss.logmanager.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PollutionClientImplTest {

    private final Logger rootLogger = Logger.getLogger(PollutionClientImpl.class.getName());
    private QueuedHandler handler;

    private final PollutionResource pollutionResource = Mockito.mock(PollutionResource.class);
    private final Emitter<String> emitter = (Emitter<String>) Mockito.mock(Emitter.class);
    private final PollutionClient sut = new PollutionClientImpl(pollutionResource, emitter);

    @BeforeEach
    public void setup() {
        handler = new PollutionClientImplTest.QueuedHandler();
        rootLogger.addHandler(handler);
        rootLogger.setLevel(Level.ALL);
    }

    @AfterEach
    public void tearDown() {
        rootLogger.removeHandler(handler);
        handler.close();
    }


    @Test
    @DisplayName("Ensure station ID is always printed first.")
    void ensureStationIdOrdering() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
        });
        when(emitter.send(anyString())).thenReturn(voidCompletableFuture);

        sut.postPollutionData(new PollutionData(1, "instant", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));

        // obtain log event for payload
        Optional<ExtLogRecord> optionalInfoLogEvent = handler.queue.stream()
            .filter(extLogRecord -> extLogRecord.getMessage().contains("Sending payload to Pollution Channel:"))
            .findFirst();
        assertTrue(optionalInfoLogEvent.isPresent());
        assertEquals(optionalInfoLogEvent.get().getLevel(), java.util.logging.Level.INFO);

        // check ordering
        final String payload = optionalInfoLogEvent.get().getMessage();
        int stationIdIndex = payload.indexOf("stationId");
        int instantIndex = payload.indexOf("instant");
        int pm1_0Index = payload.indexOf("PM1_0");
        int pm2_5Index = payload.indexOf("PM2_5");
        int pm10Index = payload.indexOf("PM10");
        int pm1_0_AtmIndex = payload.indexOf("PM1_0_atm");
        int pm2_5_AtmIndex = payload.indexOf("PM2_5_atm");
        int pm10_AtmIndex = payload.indexOf("PM10_atm");
        int gt0_3umIndex = payload.indexOf("gt0_3um");
        int gt0_5umIndex = payload.indexOf("gt0_5um");
        int gt1_0umIndex = payload.indexOf("gt1_0um");
        int gt2_5umIndex = payload.indexOf("gt2_5um");
        int gt5_0umIndex = payload.indexOf("gt5_0um");
        int gt10umIndex = payload.indexOf("gt10um");

        assertTrue(stationIdIndex < instantIndex, "stationId should be located before instant.");
        assertTrue(instantIndex < pm1_0Index, "instant should be located before pm1_0.");
        assertTrue(pm1_0Index < pm2_5Index, "pm1_0 should be located before pm2_5.");
        assertTrue(pm2_5Index < pm10Index, "pm2_5 should be located before pm10.");
        assertTrue(pm10Index < pm1_0_AtmIndex, "pm10 should be located before pm1_0_Atm.");
        assertTrue(pm1_0_AtmIndex < pm2_5_AtmIndex, "pm1_0_Atm should be located before pm2_5_Atm.");
        assertTrue(pm2_5_AtmIndex < pm10_AtmIndex, "pm2_5_Atm should be located before pm10_Atm.");
        assertTrue(pm10_AtmIndex < gt0_3umIndex, "pm10_Atm should be located before gt0_3um.");
        assertTrue(gt0_3umIndex < gt0_5umIndex, "gt0_3um should be located before gt0_5um.");
        assertTrue(gt0_5umIndex < gt1_0umIndex, "gt0_5um should be located before gt1_0um.");
        assertTrue(gt1_0umIndex < gt2_5umIndex, "gt1_0um should be located before gt2_5um.");
        assertTrue(gt2_5umIndex < gt5_0umIndex, "gt2_5um should be located before gt5_0um.");
        assertTrue(gt5_0umIndex < gt10umIndex, "gt5_0um should be located before gt10um.");
    }

    @Test
    @DisplayName("In case the CompletableFuture returns exceptionally, ensure we log both the info and error message.")
    void testLogEventsInCaseOfError() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            throw new UnsupportedOperationException();
        });
        when(emitter.send(anyString())).thenReturn(voidCompletableFuture);

        sut.postPollutionData(new PollutionData(1, "instant", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));

        Optional<ExtLogRecord> optionalInfoLogEvent = handler.queue.stream()
            .filter(extLogRecord -> extLogRecord.getMessage().contains("Sending payload to Pollution Channel:"))
            .findFirst();
        assertTrue(optionalInfoLogEvent.isPresent());
        assertEquals(optionalInfoLogEvent.get().getLevel(), java.util.logging.Level.INFO);

        Optional<ExtLogRecord> optionalErrorLogEvent = handler.queue.stream()
            .filter(extLogRecord -> extLogRecord.getMessage().contains("Failed to send message. Error")).findFirst();
        assertTrue(optionalErrorLogEvent.isPresent());
        assertEquals(optionalErrorLogEvent.get().getLevel(), java.util.logging.Level.SEVERE);
    }

    private static class QueuedHandler extends ExtHandler {

        final BlockingDeque<ExtLogRecord> queue;

        private QueuedHandler() {
            queue = new LinkedBlockingDeque<>();
        }

        @Override
        protected void doPublish(final ExtLogRecord record) {
            // Ensures the caller is calculated for testing
            record.copyAll();
            queue.addLast(record);
        }

        @Override
        public void close() {
            try {
                queue.clear();
            } finally {
                super.close();
            }
        }
    }
}
