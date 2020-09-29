package nl.devoteam.gas;


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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class GasClientImplTest {

    private final Logger rootLogger = Logger.getLogger(GasClientImpl.class.getName());
    private QueuedHandler handler;

    private final GasResource gasResource = Mockito.mock(GasResource.class);
    private final Emitter<String> emitter = (Emitter<String>) Mockito.mock(Emitter.class);
    private final GasClient sut = new GasClientImpl(gasResource, emitter);

    @BeforeEach
    public void setup() {
        handler = new QueuedHandler();
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

        sut.postGasData(new GasData(11, "instant", 1.1, 2.2, 3.3, 4.4));

        // obtain log event for payload
        Optional<ExtLogRecord> optionalInfoLogEvent = handler.queue.stream()
            .filter(extLogRecord -> extLogRecord.getMessage().contains("Sending payload to Gas Channel")).findFirst();
        assertTrue(optionalInfoLogEvent.isPresent());
        assertEquals(optionalInfoLogEvent.get().getLevel(), java.util.logging.Level.INFO);

        // check ordering
        final String payload = optionalInfoLogEvent.get().getMessage();
        int stationIdIndex = payload.indexOf("stationId");
        int instantIndex = payload.indexOf("instant");
        int adcIndex = payload.indexOf("adc");
        int nh3Index = payload.indexOf("nh3");
        int oxidisingIndex = payload.indexOf("oxidising");
        int reducingIndex = payload.indexOf("reducing");

        assertTrue(stationIdIndex < instantIndex, "stationId should be located before instant.");
        assertTrue(instantIndex < adcIndex, "instant should be located before adc.");
        assertTrue(adcIndex < nh3Index, "adc should be located before nh3.");
        assertTrue(nh3Index < oxidisingIndex, "nh3 should be located before oxidising.");
        assertTrue(oxidisingIndex < reducingIndex, "oxidising should be located before reducing.");
    }

    @Test
    @Disabled("Flaky test. :-(")
    @DisplayName("In case the CompletableFuture returns exceptionally, ensure we log both the info and error message.")
    void testLogEventsInCaseOfError() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            throw new UnsupportedOperationException();
        });
        when(emitter.send(anyString())).thenReturn(voidCompletableFuture);

        sut.postGasData(new GasData(11, "instant", 1.1, 2.2, 3.3, 4.4));

        Optional<ExtLogRecord> optionalInfoLogEvent = handler.queue.stream()
            .filter(extLogRecord -> extLogRecord.getMessage().contains("Sending payload to Gas Channel")).findFirst();
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

