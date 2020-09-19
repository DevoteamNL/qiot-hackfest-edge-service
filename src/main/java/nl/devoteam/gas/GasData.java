package nl.devoteam.gas;


import java.util.StringJoiner;

/**
 * Data class for gas data.
 */
public class GasData {

    /**
     * Timestamp in ISO-8601 format.
     */
    private final int stationId;
    private final String instant;
    private final double adc;
    private final double nh3;
    private final double oxidising;
    private final double reducing;

    public GasData(int stationId, String instant, double adc, double nh3, double oxidising, double reducing) {
        this.stationId = stationId;
        this.instant = instant;
        this.adc = adc;
        this.nh3 = nh3;
        this.oxidising = oxidising;
        this.reducing = reducing;
    }

    public int getStationId() {
        return stationId;
    }

    public String getInstant() {
        return instant;
    }

    public double getAdc() {
        return adc;
    }

    public double getNh3() {
        return nh3;
    }

    public double getOxidising() {
        return oxidising;
    }

    public double getReducing() {
        return reducing;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GasData.class.getSimpleName() + "[", "]")
            .add("stationId=" + stationId)
            .add("instant='" + instant + "'")
            .add("adc=" + adc)
            .add("nh3=" + nh3)
            .add("oxidising=" + oxidising)
            .add("reducing=" + reducing)
            .toString();
    }
}
