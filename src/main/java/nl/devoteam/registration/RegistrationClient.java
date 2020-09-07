package nl.devoteam.registration;

public interface RegistrationClient {

    /**
     * Registers the device.
     * This operation returns an ID, which is required for communication with the data hub platform.
     * @param latitude
     * @param longitude
     * @param name
     * @param serial
     * @return int
     */
    int registerDevice(double latitude, double longitude, String name, String serial);

    /**
     * Unregisters the device.
     * The registration id should be passed to remove the registration.
     * @param id
     */
    void unregisterDevice(int id);
}
