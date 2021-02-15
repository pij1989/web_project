package com.pozharsky.dmitri.model.error;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ApplicationError {
    private static final Lock lock = new ReentrantLock();
    private static final AtomicBoolean isInstance = new AtomicBoolean(false);
    private static ApplicationError instance;
    private final ThreadLocal<List<ErrorType>> threadLocal = new ThreadLocal<>();

    private ApplicationError() {
    }

    public static ApplicationError getInstance() {
        if (!isInstance.get()) {
            lock.lock();
            try {
                if (!isInstance.get()) {
                    instance = new ApplicationError();
                    isInstance.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public boolean hasErrors() {
        return threadLocal.get() != null;
    }

    public void addError(ErrorType error) {
        if (threadLocal.get() == null) {
            List<ErrorType> errorTypes = new ArrayList<>();
            errorTypes.add(error);
            threadLocal.set(errorTypes);
        } else {
            List<ErrorType> errors = threadLocal.get();
            errors.add(error);
        }
    }

    public List<ErrorType> getErrors() {
        if (threadLocal.get() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(threadLocal.get());
    }

    public void clearErrors() {
        threadLocal.remove();
    }
}
