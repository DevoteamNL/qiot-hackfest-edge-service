package nl.devoteam.registration.impl;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.enterprise.context.ApplicationScoped;
import nl.devoteam.registration.DeviceMetadataService;

@ApplicationScoped
public class DeviceMetadataServiceImpl implements DeviceMetadataService {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private int id;

    @Override
    public int getRegistrationId() {
        lock.readLock().lock();
        try {
            return id;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void setRegistrationId(int registrationId) {
        lock.writeLock().lock();
        try {
            id = registrationId;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
