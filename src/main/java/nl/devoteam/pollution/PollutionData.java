package nl.devoteam.pollution;


import java.util.StringJoiner;

/**
 * Data class for pollution data.
 */
public class PollutionData {

    /**
     * Timestamp in ISO-8601 format.
     */
    private final String instant;
    private final int PM1_0;
    private final int PM2_5;
    private final int PM10;
    private final int PM1_0_atm;
    private final int PM2_5_atm;
    private final int PM10_atm;
    private final int gt0_3um;
    private final int gt0_5um;
    private final int gt1_0um;
    private final int gt2_5um;
    private final int gt5_0um;
    private final int gt10um;

    public PollutionData(String instant, int PM1_0, int PM2_5, int PM10, int PM1_0_atm, int PM2_5_atm, int PM10_atm,
        int gt0_3um, int gt0_5um, int gt1_0um, int gt2_5um, int gt5_0um, int gt10um) {
        this.instant = instant;
        this.PM1_0 = PM1_0;
        this.PM2_5 = PM2_5;
        this.PM10 = PM10;
        this.PM1_0_atm = PM1_0_atm;
        this.PM2_5_atm = PM2_5_atm;
        this.PM10_atm = PM10_atm;
        this.gt0_3um = gt0_3um;
        this.gt0_5um = gt0_5um;
        this.gt1_0um = gt1_0um;
        this.gt2_5um = gt2_5um;
        this.gt5_0um = gt5_0um;
        this.gt10um = gt10um;
    }

    public String getInstant() {
        return instant;
    }

    public int getPM1_0() {
        return PM1_0;
    }

    public int getPM2_5() {
        return PM2_5;
    }

    public int getPM10() {
        return PM10;
    }

    public int getPM1_0_atm() {
        return PM1_0_atm;
    }

    public int getPM2_5_atm() {
        return PM2_5_atm;
    }

    public int getPM10_atm() {
        return PM10_atm;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", PollutionData.class.getSimpleName() + "[", "]")
            .add("instant='" + instant + "'")
            .add("PM1_0=" + PM1_0)
            .add("PM2_5=" + PM2_5)
            .add("PM10=" + PM10)
            .add("PM1_0_atm=" + PM1_0_atm)
            .add("PM2_5_atm=" + PM2_5_atm)
            .add("PM10_atm=" + PM10_atm)
            .add("gt0_3um=" + gt0_3um)
            .add("gt0_5um=" + gt0_5um)
            .add("gt1_0um=" + gt1_0um)
            .add("gt2_5um=" + gt2_5um)
            .add("gt5_0um=" + gt5_0um)
            .add("gt10um=" + gt10um)
            .toString();
    }
}
