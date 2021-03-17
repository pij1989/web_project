package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.OrderDao;
import com.pozharsky.dmitri.model.dao.OrderProductDao;
import com.pozharsky.dmitri.model.dao.ProductDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private static final OrderServiceImpl instance = new OrderServiceImpl();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Order> addNewOrder(long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderDao orderDao = new OrderDao();
            transactionManager.init(orderDao);
            LocalDateTime timeCreating = LocalDateTime.now();
            BigDecimal cost = BigDecimal.ZERO;
            Order.StatusType status = Order.StatusType.NEW;
            Order order = new Order(cost, timeCreating, status, userId);
            Optional<Long> optionalLong = orderDao.create(order);
            if (optionalLong.isPresent()) {
                long orderId = optionalLong.get();
                order.setId(orderId);
                return Optional.of(order);
            }
            return Optional.empty();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

   /* @Override
    public boolean addNewOrder(String amountProduct, Product product, long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isAdd = false;
            OrderDao orderDao = new OrderDao();
            OrderProductDao orderProductDao = new OrderProductDao();
            transactionManager.initTransaction(orderDao, orderProductDao);
            LocalDateTime timeCreating = LocalDateTime.now();
            int amount = Integer.parseInt(amountProduct);
            BigDecimal cost = calculateCost(amount, product.getPrice());
            Order order = new Order(cost, timeCreating, Order.StatusType.NEW, userId);
            Optional<Long> optionalLong = orderDao.create(order);
            if (optionalLong.isPresent()) {
                long orderId = optionalLong.get();
                order.setId(orderId);
                OrderProduct orderProduct = new OrderProduct(amount, product, order);
                isAdd = orderProductDao.create(orderProduct);
            }
            transactionManager.commit();
            return isAdd;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }*/

    @Override
    public Optional<Order> findNewOrder(long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderDao orderDao = new OrderDao();
            transactionManager.init(orderDao);
            List<Order> orders = orderDao.findByUserIdAndStatus(userId, Order.StatusType.NEW);
            return orders.stream()
                    .findFirst();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public boolean addProductToOrder(String amountProduct, Product product, Order order) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isAdd = false;
            OrderProductDao orderProductDao = new OrderProductDao();
            OrderDao orderDao = new OrderDao();
            ProductDao productDao = new ProductDao();
            transactionManager.initTransaction(orderProductDao, orderDao, productDao);
            int amount = Integer.parseInt(amountProduct);
            if (productDao.decreaseAmountById(amount, product.getId())) {
                isAdd = orderProductDao.updateAmountProductByOrderIdAndProductId(amount, order.getId(), product.getId());
                if (!isAdd) {
                    OrderProduct orderProduct = new OrderProduct(amount, product, order);
                    isAdd = orderProductDao.create(orderProduct);
                }
                if (isAdd) {
                    BigDecimal cost = calculateCost(amount, product.getPrice());
                    isAdd = orderDao.increaseCostById(cost, order.getId());
                }
            }
            transactionManager.commit();
            return isAdd;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public List<OrderProduct> findProductInNewOrder(long orderId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderProductDao orderProductDao = new OrderProductDao();
            transactionManager.init(orderProductDao);
            return orderProductDao.findByOrderId(orderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public Optional<OrderProduct> deleteProductFromOrder(String orderProductId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderProductDao orderProductDao = new OrderProductDao();
            ProductDao productDao = new ProductDao();
            OrderDao orderDao = new OrderDao();
            transactionManager.initTransaction(orderProductDao, productDao, orderDao);
            long id = Long.parseLong(orderProductId);
            Optional<OrderProduct> optionalOrderProduct = orderProductDao.findById(id);
            if (optionalOrderProduct.isPresent()) {
                if (orderProductDao.delete(id)) {
                    OrderProduct orderProduct = optionalOrderProduct.get();
                    long orderId = orderProduct.getOrder().getId();
                    int amountProduct = orderProduct.getAmount();
                    long productId = orderProduct.getProduct().getId();
                    BigDecimal totalPrice = orderProduct.getTotalPrice();
                    orderDao.decreaseCostById(totalPrice, orderId);
                    productDao.increaseAmountById(amountProduct, productId);
                }
            }
            transactionManager.commit();
            return optionalOrderProduct;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    private BigDecimal calculateCost(int amount, BigDecimal price) {
        return price.multiply(new BigDecimal(amount));
    }
}
