package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Address;
import com.pozharsky.dmitri.model.entity.Delivery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Class DeliveryDao is used to interact with deliveries table in the database.
 *
 * @author Dmitri Pozharsky
 */
public class DeliveryDao extends AbstractDao<Delivery> {
    private static final Logger logger = LogManager.getLogger(DeliveryDao.class);
    private static final String CREATE_DELIVERY_SQL = "INSERT INTO deliveries (first_name, last_name, city, street, home_number, phone_number, order_id) VALUES (?,?,?,?,?,?,?);";
    private static final String FIND_DELIVERY_BY_ORDER_ID_SQL = "SELECT first_name, last_name, city, street, home_number, phone_number FROM deliveries WHERE order_id = ?;";

    /**
     * Create new delivery in database.
     *
     * @param delivery Delivery object will be created in the database.
     * @return Not empty Optional with id of creating category if it has been created, otherwise Optional.empty().
     * @throws DaoException if the database throws SQLException.
     */
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

    /**
     * Find delivery by order's ID.
     *
     * @param orderId order's id long value..
     * @return Not empty Optional entity object if it was found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    public Optional<Delivery> findByOrderId(long orderId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_DELIVERY_BY_ORDER_ID_SQL)) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Delivery delivery = createDeliveryFromResultSet(resultSet);
                return Optional.of(delivery);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional findById(long id) {
        throw new UnsupportedOperationException("Unsupported operation 'findById' for DeliveryDao");
    }

    @Override
    public List findAll() {
        throw new UnsupportedOperationException("Unsupported operation 'findAll' for DeliveryDao");
    }

    @Override
    public Optional update(Delivery entity) {
        throw new UnsupportedOperationException("Unsupported operation 'update' for DeliveryDao");
    }

    @Override
    public boolean deleteById(long id) {
        throw new UnsupportedOperationException("Unsupported operation 'deleteById' for DeliveryDao");
    }

    private Delivery createDeliveryFromResultSet(ResultSet resultSet) throws SQLException {
        Delivery delivery = new Delivery();
        delivery.setFirstName(resultSet.getString(ColumnName.FIRST_NAME));
        delivery.setLastName(resultSet.getString(ColumnName.LAST_NAME));
        String city = resultSet.getString(ColumnName.CITY);
        String street = resultSet.getString(ColumnName.STREET);
        String homeNumber = resultSet.getString(ColumnName.HOME_NUMBER);
        Address address = new Address(city, street, homeNumber);
        delivery.setAddress(address);
        delivery.setPhoneNumber(resultSet.getString(ColumnName.PHONE_NUMBER));
        return delivery;
    }
}
