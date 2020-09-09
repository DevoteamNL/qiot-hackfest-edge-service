package nl.devoteam.registration.model;

import java.util.StringJoiner;

/**
 * Data class for geo location.
 */
public class GeoPosition {

    private final double longitude;
    private final double latitude;

    public GeoPosition(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GeoPosition.class.getSimpleName() + "[", "]")
            .add("longitude=" + longitude)
            .add("latitude=" + latitude)
            .toString();
    }
}
