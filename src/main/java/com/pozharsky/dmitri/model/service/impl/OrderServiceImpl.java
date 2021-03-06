package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.creator.DeliveryCreator;
import com.pozharsky.dmitri.model.dao.*;
import com.pozharsky.dmitri.model.entity.Delivery;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.validator.DeliveryValidator;
import com.pozharsky.dmitri.validator.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * OrderService implementation.
 *
 * @author Dmitri Pozharsky
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private static final int ZERO = 0;
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

    @Override
    public List<Order> findAllOrders() throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderDao orderDao = new OrderDao();
            transactionManager.init(orderDao);
            return orderDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public Optional<Order> findOrderById(long orderId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderDao orderDao = new OrderDao();
            transactionManager.init(orderDao);
            return orderDao.findById(orderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

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
    public List<Order> findNotNewOrders(long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderDao orderDao = new OrderDao();
            transactionManager.init(orderDao);
            return orderDao.findByUserIdAndNotStatus(userId, Order.StatusType.NEW);
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
                    orderDao.increaseCostById(cost, order.getId());
                    increaseCostOfOrder(cost, order);
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
    public List<OrderProduct> findProductInOrder(long orderId) throws ServiceException {
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
    public Optional<Delivery> findOrderDelivery(long orderId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            DeliveryDao deliveryDao = new DeliveryDao();
            transactionManager.init(deliveryDao);
            return deliveryDao.findByOrderId(orderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public Optional<OrderProduct> deleteProductFromOrder(String orderProductId, Order order) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderProductDao orderProductDao = new OrderProductDao();
            ProductDao productDao = new ProductDao();
            OrderDao orderDao = new OrderDao();
            transactionManager.initTransaction(orderProductDao, productDao, orderDao);
            long id = Long.parseLong(orderProductId);
            Optional<OrderProduct> optionalOrderProduct = orderProductDao.findById(id);
            if (optionalOrderProduct.isPresent()) {
                if (orderProductDao.deleteById(id)) {
                    OrderProduct orderProduct = optionalOrderProduct.get();
                    long orderId = orderProduct.getOrder().getId();
                    int amountProduct = orderProduct.getAmount();
                    long productId = orderProduct.getProduct().getId();
                    BigDecimal totalPrice = orderProduct.getTotalPrice();
                    orderDao.decreaseCostById(totalPrice, orderId);
                    productDao.increaseAmountById(amountProduct, productId);
                    decreaseCostOfOrder(totalPrice, order);
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

    @Override
    public Optional<OrderProduct> changeAmountProductInOrder(String orderProductId, String amountProduct, Order order) throws ServiceException {
        if (!OrderValidator.isValidAmountProduct(amountProduct)) {
            return Optional.empty();
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderDao orderDao = new OrderDao();
            ProductDao productDao = new ProductDao();
            OrderProductDao orderProductDao = new OrderProductDao();
            transactionManager.initTransaction(orderDao, productDao, orderProductDao);
            long id = Long.parseLong(orderProductId);
            int newAmountProduct = Integer.parseInt(amountProduct);
            Optional<OrderProduct> optionalOrderProduct = orderProductDao.findById(id);
            if (optionalOrderProduct.isPresent()) {
                OrderProduct orderProduct = optionalOrderProduct.get();
                long orderId = orderProduct.getOrder().getId();
                long productId = orderProduct.getProduct().getId();
                int oldAmountProduct = orderProduct.getAmount();
                int difAmountProduct = newAmountProduct - oldAmountProduct;
                if (difAmountProduct < ZERO) {
                    if (orderProductDao.updateAmountProductByOrderIdAndProductId(difAmountProduct, orderId, productId)) {
                        BigDecimal difTotalPrice = updateCostOfOrder(orderDao, orderProduct, difAmountProduct);
                        productDao.decreaseAmountById(difAmountProduct, productId);
                        increaseCostOfOrder(difTotalPrice, order);
                    } else {
                        optionalOrderProduct = Optional.empty();
                    }
                } else {
                    if (productDao.decreaseAmountById(difAmountProduct, productId)) {
                        if (orderProductDao.updateAmountProductByOrderIdAndProductId(difAmountProduct, orderId, productId)) {
                            BigDecimal difTotalPrice = updateCostOfOrder(orderDao, orderProduct, difAmountProduct);
                            increaseCostOfOrder(difTotalPrice, order);
                        } else {
                            transactionManager.rollback();
                            optionalOrderProduct = Optional.empty();
                        }
                    } else {
                        optionalOrderProduct = Optional.empty();
                    }
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

    @Override
    public boolean confirmNewOrder(long orderId, Map<String, String> deliveryForm, Order.StatusType orderStatusType) throws ServiceException {
        if (!DeliveryValidator.isValidDeliveryForm(deliveryForm)) {
            return false;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isConfirm = false;
            OrderDao orderDao = new OrderDao();
            DeliveryDao deliveryDao = new DeliveryDao();
            transactionManager.initTransaction(orderDao, deliveryDao);
            Delivery delivery = DeliveryCreator.createDelivery(deliveryForm, orderId);
            LocalDateTime localDateTime = LocalDateTime.now();
            if (deliveryDao.create(delivery)) {
                isConfirm = orderDao.updateStatusAndTimeCreateById(orderId, localDateTime, orderStatusType);
            }
            transactionManager.commit();
            return isConfirm;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public boolean changeOrderStatus(long orderId, Order.StatusType statusType) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isChange;
            ProductDao productDao = new ProductDao();
            OrderProductDao orderProductDao = new OrderProductDao();
            OrderDao orderDao = new OrderDao();
            transactionManager.initTransaction(orderDao, productDao, orderProductDao);
            if (statusType == Order.StatusType.CANCELED) {
                canceledOrder(orderId, statusType, orderProductDao, productDao, orderDao);
            }
            if (statusType == Order.StatusType.PROCESSING || statusType == Order.StatusType.DELIVERED) {
                processOrder(orderId, orderProductDao, productDao, orderDao);
            }
            isChange = orderDao.updateStatusById(orderId, statusType);
            transactionManager.commit();
            return isChange;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public boolean deleteOrder(long orderId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            OrderDao orderDao = new OrderDao();
            transactionManager.init(orderDao);
            return orderDao.deleteById(orderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    private BigDecimal calculateCost(int amount, BigDecimal price) {
        return price.multiply(new BigDecimal(amount));
    }

    private void increaseCostOfOrder(BigDecimal cost, Order order) {
        BigDecimal previousCost = order.getCost();
        BigDecimal actualCost = previousCost.add(cost);
        order.setCost(actualCost);
    }

    private void decreaseCostOfOrder(BigDecimal cost, Order order) {
        BigDecimal previousCost = order.getCost();
        BigDecimal actualCost = previousCost.subtract(cost);
        order.setCost(actualCost);
    }

    private BigDecimal updateCostOfOrder(OrderDao orderDao, OrderProduct orderProduct, int difAmountProduct) throws DaoException {
        BigDecimal price = orderProduct.getProduct().getPrice();
        long orderId = orderProduct.getOrder().getId();
        BigDecimal difTotalPrice = calculateCost(difAmountProduct, price);
        orderDao.increaseCostById(difTotalPrice, orderId);
        return difTotalPrice;
    }

    private void canceledOrder(long orderId, Order.StatusType statusType, OrderProductDao orderProductDao,
                               ProductDao productDao, OrderDao orderDao) throws DaoException {
        Optional<Order> optionalOrder = orderDao.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Order.StatusType status = order.getStatusType();
            if (status != statusType) {
                List<OrderProduct> orderProducts = orderProductDao.findByOrderId(orderId);
                for (OrderProduct orderProduct : orderProducts) {
                    long productId = orderProduct.getProduct().getId();
                    int amountProduct = orderProduct.getAmount();
                    productDao.increaseAmountById(amountProduct, productId);
                }
            }
        }
    }

    private void processOrder(long orderId, OrderProductDao orderProductDao,
                              ProductDao productDao, OrderDao orderDao) throws DaoException {
        Optional<Order> optionalOrder = orderDao.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Order.StatusType status = order.getStatusType();
            if (status == Order.StatusType.CANCELED) {
                List<OrderProduct> orderProducts = orderProductDao.findByOrderId(orderId);
                for (OrderProduct orderProduct : orderProducts) {
                    long productId = orderProduct.getProduct().getId();
                    int amountProduct = orderProduct.getAmount();
                    productDao.decreaseAmountById(amountProduct, productId);
                }
            }
        }
    }
}
