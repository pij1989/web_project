package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderProductDao extends AbstractDao<OrderProduct> {
    private static final Logger logger = LogManager.getLogger(OrderProductDao.class);
    private static final String CREATE_ORDER_PRODUCT_SQL = "INSERT INTO order_products(amount_product, order_id, product_id) VALUES (?,?,?);";
    private static final String FIND_ORDER_PRODUCT_BY_ID_SQL = "SELECT op.id, op.amount_product, op.product_id, op.order_id, p.product_name, p.price, p.amount, p.image, p.price*op.amount_product AS total_price FROM order_products AS op JOIN products AS p on op.product_id = p.id WHERE op.id = ?;";
    private static final String FIND_ORDER_PRODUCT_BY_USER_ID_AND_STATUS_SQL = "SELECT op.id, op.amount_product, op.product_id, op.order_id, p.product_name, p.price, p.amount, p.image, p.price*op.amount_product AS total_price FROM orders AS o JOIN order_products AS op on o.id = op.order_id JOIN products AS p on op.product_id = p.id JOIN orders_status AS os on o.order_status_id = os.id WHERE o.user_id = ? AND os.order_status_name = ?;";
    private static final String FIND_ORDER_PRODUCT_BY_ORDER_ID_SQL = "SELECT op.id, op.amount_product, op.product_id, op.order_id, p.product_name, p.price, p.amount, p.image, p.price*op.amount_product AS total_price FROM order_products AS op JOIN products AS p on op.product_id = p.id WHERE op.order_id = ?;";
    private static final String UPDATE_AMOUNT_PRODUCT_BY_ORDER_ID_AND_PRODUCT_ID_SQL = "UPDATE order_products SET amount_product = amount_product + ? WHERE order_id = ? AND product_id = ?;";
    private static final String DELETE_ORDER_PRODUCT_BY_ID_SQL = "DELETE FROM order_products WHERE id = ?";

    public boolean create(OrderProduct orderProduct) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER_PRODUCT_SQL)) {
            Order order = orderProduct.getOrder();
            Product product = orderProduct.getProduct();
            preparedStatement.setInt(1, orderProduct.getAmount());
            preparedStatement.setLong(2, order.getId());
            preparedStatement.setLong(3, product.getId());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<OrderProduct> findByUserIdAndStatus(long userId, Order.StatusType statusType) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_PRODUCT_BY_USER_ID_AND_STATUS_SQL)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, statusType.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListOrderProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<OrderProduct> findByOrderId(long orderId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_PRODUCT_BY_ORDER_ID_SQL)) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListOrderProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public boolean updateAmountProductByOrderIdAndProductId(int amount, long orderId, long productId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AMOUNT_PRODUCT_BY_ORDER_ID_AND_PRODUCT_ID_SQL)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setLong(2, orderId);
            preparedStatement.setLong(3, productId);
            int resultUpdate = preparedStatement.executeUpdate();
            return resultUpdate > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<OrderProduct> findById(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_PRODUCT_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                OrderProduct orderProduct = createOrderProductFromResultSet(resultSet);
                return Optional.of(orderProduct);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderProduct> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<OrderProduct> update(OrderProduct entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(OrderProduct entity) throws DaoException {
        return false;
    }

    public boolean delete(long orderProductId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_PRODUCT_BY_ID_SQL)) {
            preparedStatement.setLong(1, orderProductId);
            int resultDelete = preparedStatement.executeUpdate();
            return resultDelete > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    private OrderProduct createOrderProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(ColumnName.PRODUCT_ID));
        product.setName(resultSet.getString(ColumnName.PRODUCT_NAME));
        product.setAmount(resultSet.getInt(ColumnName.AMOUNT));
        product.setPrice(resultSet.getBigDecimal(ColumnName.PRICE));
        product.setImage(resultSet.getBytes(ColumnName.IMAGE));
        Order order = new Order();
        order.setId(resultSet.getLong(ColumnName.ORDER_ID));
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setId(resultSet.getLong(ColumnName.ID));
        orderProduct.setAmount(resultSet.getInt(ColumnName.AMOUNT_PRODUCT));
        orderProduct.setTotalPrice(resultSet.getBigDecimal(ColumnName.TOTAL_PRICE));
        return orderProduct;
    }

    private List<OrderProduct> createListOrderProductFromResultSet(ResultSet resultSet) throws SQLException {
        List<OrderProduct> orderProducts = new ArrayList<>();
        while (resultSet.next()) {
            OrderProduct orderProduct = createOrderProductFromResultSet(resultSet);
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }
}
