package net.thumbtack.asurovenko.tasks.task12;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class MyConcurrentHashMap  extends HashMap {
    private ReadWriteLock lock;

    public MyConcurrentHashMap(int initialCapacity, float loadFactor, ReadWriteLock lock) {
        super(initialCapacity, loadFactor);
        this.lock = lock;
    }

    public MyConcurrentHashMap(int initialCapacity, ReadWriteLock lock) {
        super(initialCapacity);
        this.lock = lock;
    }

    public MyConcurrentHashMap(ReadWriteLock lock) {
        this.lock = lock;
    }

    public MyConcurrentHashMap(Map m, ReadWriteLock lock) {
        super(m);
        this.lock = lock;
    }

    @Override
    public Object put(Object key, Object value) {
        lock.writeLock().lock();
        try {
            return super.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Object get(Object key) {
        lock.readLock().lock();
        try {
            return super.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
}
