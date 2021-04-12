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

/**
 * AbstractDao is abstract class used for interactions with database tables.
 *
 * @param <T> The type of entity to work with.
 * @author Dmitri Pozharsky
 */
public abstract class AbstractDao<T extends Entity> {
    private static final Logger logger = LogManager.getLogger(AbstractDao.class);
    protected Connection connection;

    /**
     * Finds an entity by its ID.
     *
     * @param id entity's ID int value.
     * @return Not empty Optional entity object if it was found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    public abstract Optional<T> findById(long id) throws DaoException;

    /**
     * Finds list of all entities.
     *
     * @return Not empty List of entity objects if they were found, empty List otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    public abstract List<T> findAll() throws DaoException;

    /**
     * Update entity.
     *
     * @param entity entity object which should be updated.
     * @return Not empty Optional entity object if it was updated, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    public abstract Optional<T> update(T entity) throws DaoException;

    /**
     * Delete entity by entity's ID.
     *
     * @param id entity's ID int value.
     * @return boolean value is true if entity was deleted, boolean value is false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    public abstract boolean deleteById(long id) throws DaoException;

    /**
     * Invoke method close() of Statement object.
     *
     * @param statement Statement interface implementation.
     */
    public void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Impossible close statement: " + e);
            }
        }
    }

    /**
     * Set connection to Dao.
     *
     * @param connection Connection interface implementation.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
