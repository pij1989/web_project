package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Product;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    boolean createProduct(Map<String, String> productForm, Part part) throws ServiceException;

    List<Product> findProductByCategory(long categoryId) throws ServiceException;

    Optional<Product> findProductById(long productId) throws ServiceException;

    List<Product> findAllProducts() throws ServiceException;

    Optional<Product> updateProduct(Map<String, String> productForm, String productId, Part part) throws ServiceException;

    int defineAmountProduct() throws ServiceException;

    int defineAmountProductByCategory(long categoryId) throws ServiceException;

    int defineAmountActiveProductByCategory(long categoryId) throws ServiceException;

    int defineAmountActiveSearchProduct(String searchProduct) throws ServiceException;

    List<Product> findProductsByPerPage(String page, String perPage) throws ServiceException;

    List<Product> findProductsByCategoryAndPerPage(long categoryId, String page, String perPage) throws ServiceException;

    List<Product> findActiveProductsByCategoryAndPerPage(long categoryId, String page, String perPage, String sort) throws ServiceException;

    List<Product> searchProduct(String searchProduct) throws ServiceException;

    List<Product> searchActiveProduct(String searchProduct) throws ServiceException;

    List<Product> filterActiveProduct(long categoryId, Map<String, String> filterForm, String sort) throws ServiceException;

    List<Product> findLastAddActiveProduct(int limit) throws ServiceException;
}
