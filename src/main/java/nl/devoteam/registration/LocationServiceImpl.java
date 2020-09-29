package nl.devoteam.registration;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class.getName());
    private static final String QUERY = "https://nominatim.openstreetmap.org/search?q=Den%20Haag&format=json&addressdetails=1";
    private GeoPosition instance;

    @Override
    public GeoPosition obtainPosition() {
        if (instance == null) {
            this.instance = grabPosition();
        }
        return this.instance;
    }

    private GeoPosition grabPosition() {
        URL url;
        try {
            url = new URL(QUERY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        try (InputStream is = Objects.requireNonNull(url).openStream();
            JsonReader reader = Json.createReader(is)) {
            JsonArray jsonArray = reader.readArray();

            JsonObject jsonObject = jsonArray.getJsonObject(0);

            double longitude = Double
                .parseDouble(jsonObject.getString("lon"));
            double latitude = Double
                .parseDouble(jsonObject.getString("lat"));

            LOGGER.info("Resolved position to be on longitude " + longitude + ", latitude " + latitude);
            return new GeoPosition(longitude, latitude);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
