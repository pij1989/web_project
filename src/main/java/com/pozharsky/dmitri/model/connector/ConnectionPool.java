package com.pozharsky.dmitri.model.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe connection pool is used to contain, give and manage Connection objects.
 *
 * @author Dmitri Pozharsky
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final int INITIAL_POOL_SIZE = 32;
    private static final AtomicBoolean isInstance = new AtomicBoolean(false);
    private static final Lock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnection;
    private final BlockingQueue<ProxyConnection> givenAwayConnection;

    private ConnectionPool() {
        freeConnection = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
        givenAwayConnection = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                Connection connection = ConnectionCreator.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnection.offer(proxyConnection);
            }
        } catch (SQLException e) {
            logger.fatal("Can not create connection pool", e);
            throw new RuntimeException(e);
        }
    }

    public static ConnectionPool getInstance() {
        if (!isInstance.get()) {
            lock.lock();
            try {
                if (!isInstance.get()) {
                    instance = new ConnectionPool();
                    isInstance.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Gives a Connection object from the pool.
     *
     * @return Connection object.
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnection.put(connection);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Puts a Connection object back in the pool.
     *
     * @param connection Connection object that should be an instance of ProxyConnection.
     */
    void releaseConnection(Connection connection) {
        try {
            if (connection.getClass() == ProxyConnection.class) {
                givenAwayConnection.remove(connection);
                freeConnection.put((ProxyConnection) connection);
            }
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Destroys a connection pool. Should be called before finishing the program.
     */
    public void destroyPool() {
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                freeConnection.take().reallyClose();
            }
            deregisterDrivers();
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(e -> {
            try {
                DriverManager.deregisterDriver(e);
            } catch (SQLException ex) {
                logger.error("Can not deregister driver: " + ex);
            }
        });
    }
}
