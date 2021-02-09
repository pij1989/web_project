package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Product;

import java.util.List;

public interface ProductService {
    boolean createProduct(String productName, String category, String price, String isActive, String description, byte[] image) throws ServiceException;

    List<Product> findAllProducts() throws ServiceException;
}
