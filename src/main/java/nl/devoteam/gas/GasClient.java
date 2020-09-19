package nl.devoteam.gas;

public interface GasClient {

    /**
     * Hands the gas data over to the data hub over MQTT.
     */
    void postGasData(GasData gasData);


    /**
     * Obtains the current gas data from the sensor service.
     *
     * @return the current gas data.
     */
    GasData receiveGasData(int stationId);
}
