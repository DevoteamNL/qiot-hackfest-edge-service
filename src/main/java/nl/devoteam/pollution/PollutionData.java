package nl.devoteam.pollution;


import java.util.StringJoiner;

/**
 * Data class for pollution data.
 */
public class PollutionData {

    /**
     * Timestamp in ISO-8601 format.
     */
    private int stationId;
    private String instant;
    private int pm1_0;
    private int pm2_5;
    private int pm10;
    private int pm1_0_atm;
    private int pm2_5_atm;
    private int pm10_atm;
    private int gt0_3um;
    private int gt0_5um;
    private int gt1_0um;
    private int gt2_5um;
    private int gt5_0um;
    private int gt10um;

    public PollutionData() {
    }

    public PollutionData(int stationId, String instant, int pm1_0, int pm2_5, int pm10, int pm1_0_atm,
        int pm2_5_atm, int pm10_atm,
        int gt0_3um, int gt0_5um, int gt1_0um, int gt2_5um, int gt5_0um, int gt10um) {
        this.stationId = stationId;
        this.instant = instant;
        this.pm1_0 = pm1_0;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        this.pm1_0_atm = pm1_0_atm;
        this.pm2_5_atm = pm2_5_atm;
        this.pm10_atm = pm10_atm;
        this.gt0_3um = gt0_3um;
        this.gt0_5um = gt0_5um;
        this.gt1_0um = gt1_0um;
        this.gt2_5um = gt2_5um;
        this.gt5_0um = gt5_0um;
        this.gt10um = gt10um;
    }

    public int getStationId() {
        return stationId;
    }

    public String getInstant() {
        return instant;
    }

    public int getPm1_0() {
        return pm1_0;
    }

    public int getPm2_5() {
        return pm2_5;
    }

    public int getPm10() {
        return pm10;
    }

    public int getPm1_0_atm() {
        return pm1_0_atm;
    }

    public int getPm2_5_atm() {
        return pm2_5_atm;
    }

    public int getPm10_atm() {
        return pm10_atm;
    }

    public int getGt0_3um() {
        return gt0_3um;
    }

    public int getGt0_5um() {
        return gt0_5um;
    }

    public int getGt1_0um() {
        return gt1_0um;
    }

    public int getGt2_5um() {
        return gt2_5um;
    }

    public int getGt5_0um() {
        return gt5_0um;
    }

    public int getGt10um() {
        return gt10um;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setInstant(String instant) {
        this.instant = instant;
    }

    public void setPm1_0(int pm1_0) {
        this.pm1_0 = pm1_0;
    }

    public void setPm2_5(int pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public void setPm1_0_atm(int pm1_0_atm) {
        this.pm1_0_atm = pm1_0_atm;
    }

    public void setPm2_5_atm(int pm2_5_atm) {
        this.pm2_5_atm = pm2_5_atm;
    }

    public void setPm10_atm(int pm10_atm) {
        this.pm10_atm = pm10_atm;
    }

    public void setGt0_3um(int gt0_3um) {
        this.gt0_3um = gt0_3um;
    }

    public void setGt0_5um(int gt0_5um) {
        this.gt0_5um = gt0_5um;
    }

    public void setGt1_0um(int gt1_0um) {
        this.gt1_0um = gt1_0um;
    }

    public void setGt2_5um(int gt2_5um) {
        this.gt2_5um = gt2_5um;
    }

    public void setGt5_0um(int gt5_0um) {
        this.gt5_0um = gt5_0um;
    }

    public void setGt10um(int gt10um) {
        this.gt10um = gt10um;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PollutionData.class.getSimpleName() + "[", "]")
            .add("stationId='" + stationId + "'")
            .add("instant='" + instant + "'")
            .add("PM1_0=" + pm1_0)
            .add("PM2_5=" + pm2_5)
            .add("PM10=" + pm10)
            .add("PM1_0_atm=" + pm1_0_atm)
            .add("PM2_5_atm=" + pm2_5_atm)
            .add("PM10_atm=" + pm10_atm)
            .add("gt0_3um=" + gt0_3um)
            .add("gt0_5um=" + gt0_5um)
            .add("gt1_0um=" + gt1_0um)
            .add("gt2_5um=" + gt2_5um)
            .add("gt5_0um=" + gt5_0um)
            .add("gt10um=" + gt10um)
            .toString();
    }
}
