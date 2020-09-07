package nl.devoteam.pollution;

public interface PollutionClient {

    /**
     * Hands the pollution data over to the data hub over MQTT.
     * @param stationId
     * @param data
     */
    void postPollutionData(int stationId, PollutionData data);

}
