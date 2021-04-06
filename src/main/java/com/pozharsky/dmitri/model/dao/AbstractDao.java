package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> {
    private static final Logger logger = LogManager.getLogger(AbstractDao.class);
    protected Connection connection;

    public abstract Optional<T> findById(long id) throws DaoException;

    public abstract List<T> findAll() throws DaoException;

    public abstract Optional<T> update(T entity) throws DaoException;

    public abstract boolean deleteById(long id) throws DaoException;

    public void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Impossible close statement: " + e);
            }
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
