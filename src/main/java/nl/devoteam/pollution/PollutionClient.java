package nl.devoteam.pollution;

public interface PollutionClient {

    /**
     * Hands the pollution data over to the data hub over MQTT.
     */
    void postPollutionData(PollutionData data);

    /**
     * Obtains the current pollution data from the sensor service.
     *
     * @return the current pollution data.
     */
    PollutionData receivePollutionData();

}
