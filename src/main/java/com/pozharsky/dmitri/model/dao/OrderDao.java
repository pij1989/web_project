package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class OrderDao is used to interact with orders table in the database.
 *
 * @author  Dmitri Pozharsky
 */
public class OrderDao extends AbstractDao<Order> {
    private static final Logger logger = LogManager.getLogger(OrderDao.class);
    private static final String CREATE_ORDER_SQL = "INSERT INTO orders(time_create, cost, order_status_id, user_id) VALUES (?,?,?,?);";
    private static final String FIND_STATUS_ID_BY_NAME_SQL = "SELECT id FROM orders_status WHERE order_status_name = ?;";
    private static final String FIND_ORDER_BY_USER_ID_STATUS_SQL = "SELECT o.id, o.time_create, o.cost, os.order_status_name, o.user_id FROM orders AS o JOIN orders_status AS os on o.order_status_id = os.id WHERE o.user_id = ? AND os.order_status_name = ?;";
    private static final String FIND_ORDER_BY_USER_ID_AND_NOT_STATUS_SQL = "SELECT o.id, o.time_create, o.cost, os.order_status_name, o.user_id FROM orders AS o JOIN orders_status AS os on o.order_status_id = os.id WHERE o.user_id = ? AND NOT os.order_status_name = ? ORDER BY time_create DESC;";
    private static final String INCREASE_COST_BY_ID = "UPDATE orders SET cost = cost + ? WHERE id = ?;";
    private static final String DECREASE_COST_BY_ID = "UPDATE orders SET cost = cost - ? WHERE id = ?;";
    private static final String UPDATE_ORDER_STATUS_AND_TIME_CREATE_BY_ID = "UPDATE orders SET order_status_id = ?, time_create = ? WHERE id = ?;";
    private static final String UPDATE_ORDER_STATUS_BY_ID = "UPDATE orders SET order_status_id = ? WHERE id = ?;";
    private static final String FIND_ORDER_BY_ID_SQL = "SELECT o.id, o.time_create, o.cost, os.order_status_name, o.user_id FROM orders AS o JOIN orders_status AS os on o.order_status_id = os.id WHERE o.id = ?;";
    private static final String FIND_ALL_ORDER_SQL = "SELECT o.id, o.time_create, o.cost, os.order_status_name, o.user_id FROM orders AS o JOIN orders_status AS os on o.order_status_id = os.id ORDER BY time_create DESC;";
    private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?;";

    /**
     * Create new order in database.
     *
     * @param order Order object will be created in the database.
     * @return Not empty Optional with id of creating order if it has been created, otherwise Optional.empty().
     * @throws DaoException if the database throws SQLException.
     */
    public Optional<Long> create(Order order) throws DaoException {
        try (PreparedStatement orderPreparedStatement = connection.prepareStatement(CREATE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statusPreparedStatement = connection.prepareStatement(FIND_STATUS_ID_BY_NAME_SQL)) {
            orderPreparedStatement.setTimestamp(1, Timestamp.valueOf(order.getCreatingTime()));
            orderPreparedStatement.setBigDecimal(2, order.getCost());
            long orderStatusId = findStatusId(statusPreparedStatement, order.getStatusType());
            orderPreparedStatement.setLong(3, orderStatusId);
            orderPreparedStatement.setLong(4, order.getUserId());
            orderPreparedStatement.executeUpdate();
            ResultSet orderKeys = orderPreparedStatement.getGeneratedKeys();
            if (orderKeys.next()) {
                long orderId = orderKeys.getLong(1);
                return Optional.of(orderId);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    /**
     * Find list orders by user's id and order's status.
     *
     * @param userId     user's id long value.
     * @param statusType order's status object of Order.StatusType.
     * @return List of Order objects if it has been found, otherwise empty List object
     * @throws DaoException if the database throws SQLException.
     */
    public List<Order> findByUserIdAndStatus(long userId, Order.StatusType statusType) throws DaoException {
        return findOrdersByUserIdAndStatus(userId, statusType, FIND_ORDER_BY_USER_ID_STATUS_SQL);
    }

    /**
     * Find list orders by user's id and not identity status of order .
     *
     * @param userId     user's id long value.
     * @param statusType order's status object of Order.StatusType.
     * @return List of Order objects if it has been found, otherwise empty List object
     * @throws DaoException if the database throws SQLException.
     */
    public List<Order> findByUserIdAndNotStatus(long userId, Order.StatusType statusType) throws DaoException {
        return findOrdersByUserIdAndStatus(userId, statusType, FIND_ORDER_BY_USER_ID_AND_NOT_STATUS_SQL);
    }

    /**
     * Increase cost of order by order id.
     *
     * @param cost    value of cost on which order's cost will be increased BigDecimal object.
     * @param orderId order's id long value.
     * @throws DaoException if the database throws SQLException.
     */
    public void increaseCostById(BigDecimal cost, long orderId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INCREASE_COST_BY_ID)) {
            preparedStatement.setBigDecimal(1, cost);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    /**
     * Decrease cost of order by order id.
     *
     * @param cost    value of cost on which order's cost will be decreased BigDecimal object.
     * @param orderId order's id long value.
     * @throws DaoException if the database throws SQLException.
     */
    public void decreaseCostById(BigDecimal cost, long orderId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DECREASE_COST_BY_ID)) {
            preparedStatement.setBigDecimal(1, cost);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    /**
     * Update status and time create by id boolean.
     *
     * @param orderId       the order id
     * @param localDateTime the local date time
     * @param statusType    the status type
     * @return the boolean
     * @throws DaoException the dao exception
     */
    public boolean updateStatusAndTimeCreateById(long orderId, LocalDateTime localDateTime, Order.StatusType statusType) throws DaoException {
        try (PreparedStatement orderPreparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_AND_TIME_CREATE_BY_ID);
             PreparedStatement statusPreparedStatement = connection.prepareStatement(FIND_STATUS_ID_BY_NAME_SQL)) {
            long orderStatusId = findStatusId(statusPreparedStatement, statusType);
            orderPreparedStatement.setLong(1, orderStatusId);
            orderPreparedStatement.setTimestamp(2, Timestamp.valueOf(localDateTime));
            orderPreparedStatement.setLong(3, orderId);
            int resultUpdate = orderPreparedStatement.executeUpdate();
            return resultUpdate > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    /**
     * Update status by id boolean.
     *
     * @param orderId    the order id
     * @param statusType the status type
     * @return the boolean
     * @throws DaoException the dao exception
     */
    public boolean updateStatusById(long orderId, Order.StatusType statusType) throws DaoException {
        try (PreparedStatement orderPreparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ID);
             PreparedStatement statusPreparedStatement = connection.prepareStatement(FIND_STATUS_ID_BY_NAME_SQL)) {
            long orderStatusId = findStatusId(statusPreparedStatement, statusType);
            orderPreparedStatement.setLong(1, orderStatusId);
            orderPreparedStatement.setLong(2, orderId);
            int resultUpdate = orderPreparedStatement.executeUpdate();
            return resultUpdate > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Order> findById(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);
                return Optional.of(order);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ORDER_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListOrderFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Order> update(Order entity) {
        throw new UnsupportedOperationException("Unsupported operation 'update' for OrderDao");
    }

    @Override
    public boolean deleteById(long orderId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_BY_ID)) {
            preparedStatement.setLong(1, orderId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    private long findStatusId(PreparedStatement statusPreparedStatement, Order.StatusType statusType) throws SQLException {
        statusPreparedStatement.setString(1, statusType.toString());
        ResultSet statusKeys = statusPreparedStatement.executeQuery();
        statusKeys.next();
        return statusKeys.getLong(ColumnName.ID);
    }

    private Order createOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong(ColumnName.ID));
        order.setCreatingTime(resultSet.getTimestamp(ColumnName.TIME_CREATE).toLocalDateTime());
        order.setCost(resultSet.getBigDecimal(ColumnName.COST));
        order.setStatusType(Order.StatusType.valueOf(resultSet.getString(ColumnName.ORDER_STATUS_NAME)));
        order.setUserId(resultSet.getLong(ColumnName.USER_ID));
        return order;
    }

    private List<Order> createListOrderFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = createOrderFromResultSet(resultSet);
            orders.add(order);
        }
        return orders;
    }

    private List<Order> findOrdersByUserIdAndStatus(long userId, Order.StatusType statusType, String findOrderByUserIdStatusSql) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(findOrderByUserIdStatusSql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, statusType.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListOrderFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
