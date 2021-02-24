package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Product;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    boolean createProduct(Map<String, String> productForm, Part part) throws ServiceException;

    List<Product> findProductByCategory(String categoryId) throws ServiceException;

    Optional<Product> findProductById(String productId) throws ServiceException;

    List<Product> findAllProducts() throws ServiceException;

    int defineAmountProduct() throws ServiceException;

    List<Product> findProductsByPerPage(String page, String perPage) throws ServiceException;
}
