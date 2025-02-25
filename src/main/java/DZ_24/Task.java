package DZ_24;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class Task<T> {
    private final Callable<? extends T> callable;
    private volatile T result;
    private volatile boolean isComputed = false;
    private volatile RuntimeException exception;
    private final ReentrantLock lock = new ReentrantLock();

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        if (isComputed) {
            if (exception != null) {
                throw exception;
            }
            return result;
        }

        lock.lock();
        try {
            if (!isComputed) {
                try {
                    result = callable.call();
                } catch (Exception e) {
                    exception = new ComputationException("Error computing the result", e);
                    throw exception;
                } finally {
                    isComputed = true;
                }
            }
        } finally {
            lock.unlock();
        }

        return result;
    }

    public static class ComputationException extends RuntimeException {
        public ComputationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

