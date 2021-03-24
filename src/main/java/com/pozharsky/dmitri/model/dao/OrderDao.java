package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Order> {
    private static final Logger logger = LogManager.getLogger(OrderDao.class);
    private static final String CREATE_ORDER_SQL = "INSERT INTO orders(time_create, cost, order_status_id, user_id) VALUES (?,?,?,?);";
    private static final String FIND_STATUS_ID_BY_NAME_SQL = "SELECT id FROM orders_status WHERE order_status_name = ?;";
    private static final String FIND_ORDER_BY_USER_ID_STATUS_SQL = "SELECT o.id, o.time_create, o.cost, os.order_status_name, o.user_id FROM orders AS o JOIN orders_status AS os on o.order_status_id = os.id WHERE o.user_id = ? AND os.order_status_name = ?;";
    private static final String INCREASE_COST_BY_ID = "UPDATE orders SET cost = cost + ? WHERE id = ?";
    private static final String DECREASE_COST_BY_ID = "UPDATE orders SET cost = cost - ? WHERE id = ?";
    private static final String UPDATE_ORDER_STATUS_BY_ID = "UPDATE orders SET order_status_id = ? WHERE id = ?";

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

    public List<Order> findByUserIdAndStatus(long userId, Order.StatusType statusType) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_BY_USER_ID_STATUS_SQL)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, statusType.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListOrderFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

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

    public boolean updateOrderStatusById(long orderId, Order.StatusType statusType) throws DaoException {
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
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> update(Order entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Order entity) throws DaoException {
        return false;
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
}
