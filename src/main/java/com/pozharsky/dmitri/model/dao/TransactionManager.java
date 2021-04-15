package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.connector.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction manager is responsible for initialization, making commit or rollback and closing transaction.
 *
 * @author Dmitri Pozharsky
 */
public class TransactionManager {
    private static final Logger logger = LogManager.getLogger(TransactionManager.class);
    private Connection connection;

    /**
     * Initialize transaction.
     *
     * @param dao  One dao object.
     * @param daos Multiple dao objects.
     * @throws DaoException if the database throws SQLException.
     */
    public void initTransaction(AbstractDao dao, AbstractDao... daos) throws DaoException {
        if (connection == null) {
            connection = ConnectionPool.getInstance().getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Impossible begin transaction: " + e);
            throw new DaoException(e);
        }
        dao.setConnection(connection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    /**
     * Setting connection to dao.
     *
     * @param dao Dao object.
     */
    public void init(AbstractDao dao) {
        if (connection == null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
        }
        dao.setConnection(connection);
    }

    /**
     * Closing connection and setting autocommit is true.
     */
    public void endTransaction() {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error("Impossible return connection to connection pool: " + e);
            }
        }
        connection = null;
    }

    /**
     * Closing connection.
     */
    public void end() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Impossible return connection to connection pool: " + e);
            }
        }
        connection = null;
    }

    /**
     * Commit transaction.
     *
     * @throws DaoException if the database throws SQLException.
     */
    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("Impossible commit transaction: " + e);
            throw new DaoException(e);
        }
    }

    /**
     * Rollback transaction.
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("Impossible rollback transaction: " + e);
        }
    }
}

