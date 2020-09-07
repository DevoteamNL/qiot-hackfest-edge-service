package nl.devoteam.gas;

public interface GasClient {

    /**
     * Hands the gas data over to the data hub over MQTT.
     * @param stationId
     * @param gasData
     */
    void postGasData(int stationId, GasData gasData);
}
