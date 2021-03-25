package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Address;
import com.pozharsky.dmitri.model.entity.Delivery;
import com.pozharsky.dmitri.model.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DeliveryDao extends AbstractDao {
    private static final Logger logger = LogManager.getLogger(DeliveryDao.class);
    private static final String CREATE_DELIVERY_SQL = "INSERT INTO deliveries (first_name, last_name, city, street, home_number, phone_number, order_id) VALUES (?,?,?,?,?,?,?);";

    public boolean create(Delivery delivery) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DELIVERY_SQL)) {
            Address address = delivery.getAddress();
            preparedStatement.setString(1, delivery.getFirstName());
            preparedStatement.setString(2, delivery.getLastName());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setString(5, address.getHomeNumber());
            preparedStatement.setString(6, delivery.getPhoneNumber());
            preparedStatement.setLong(7, delivery.getOrderId());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional update(Entity entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Entity entity) throws DaoException {
        return false;
    }
}
