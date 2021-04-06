package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Delivery;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    Optional<Order> addNewOrder(long userId) throws ServiceException;

    boolean addProductToOrder(String amountProduct, Product product, Order order) throws ServiceException;

    List<Order> findAllOrders() throws ServiceException;

    Optional<Order> findOrderById(long orderId) throws ServiceException;

    Optional<Order> findNewOrder(long userId) throws ServiceException;

    List<Order> findNotNewOrders(long userId) throws ServiceException;

    List<OrderProduct> findProductInOrder(long orderId) throws ServiceException;

    Optional<Delivery> findOrderDelivery(long orderId) throws ServiceException;

    Optional<OrderProduct> changeAmountProductInOrder(String orderProductId, String amountProduct, Order order) throws ServiceException;

    boolean confirmNewOrder(long orderId, Map<String, String> deliveryForm, Order.StatusType orderStatusType) throws ServiceException;

    boolean changeOrderStatus(long orderId, Order.StatusType statusType) throws ServiceException;

    Optional<OrderProduct> deleteProductFromOrder(String orderProductId, Order order) throws ServiceException;

    boolean deleteOrder(long orderId) throws ServiceException;
}
