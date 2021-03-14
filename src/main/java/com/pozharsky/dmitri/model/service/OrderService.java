package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    boolean addNewOrder(String amountProduct, Product product, long userId) throws ServiceException;

    Optional<Order> findNewOrder(long userId) throws ServiceException;

    boolean addProductToOrder(String amountProduct, Product product, Order order) throws ServiceException;

    List<OrderProduct> findProductInNewOrder(long userId) throws ServiceException;
}
