package com.pozharsky.dmitri.model.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final int INITIAL_POOL_SIZE = 32;

    private final BlockingQueue<ProxyConnection> freeConnection;
    private final BlockingQueue<ProxyConnection> givenAwayConnection;

    ConnectionPool() {
        freeConnection = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
        givenAwayConnection = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            Connection connection = ConnectionCreator.createConnection();
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            freeConnection.offer(proxyConnection);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnection.offer(connection);
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection.getClass() == ProxyConnection.class) {
                givenAwayConnection.remove(connection);
                freeConnection.put((ProxyConnection) connection);
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    public void destroyPool() {
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                freeConnection.take().realyClose();
            }

        } catch (InterruptedException | SQLException e) {
            logger.error(e);
        }
    }

    public void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(e -> {
            try {
                DriverManager.deregisterDriver(e);
            } catch (SQLException ex) {
                logger.error(ex);
            }
        });
    }
}
