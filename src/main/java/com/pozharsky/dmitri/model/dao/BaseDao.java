package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<T> {
    T findById(long id) throws DaoException;

    List<T> findAll() throws DaoException;

    T update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;

    default void close(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger().error("Impossible return connection to connection pool: " + e);
            }
        }
    }

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger().error("Impossible close statement: " + e);
            }
        }
    }

    default void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger().error("Impossible rollback transaction: " + e);
            }
        }
    }

    private Logger logger() {
        return LogManager.getLogger(this.getClass());
    }
}
