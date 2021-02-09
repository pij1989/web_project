package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.ProductDao;
import com.pozharsky.dmitri.model.dao.impl.ProductDaoImpl;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private static final ProductServiceImpl instance = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createProduct(String productName, String category, String price, String isActive, String description, byte[] image) throws ServiceException {
        try {
            ProductDao productDao = ProductDaoImpl.getInstance();
            Category categoryInstance = new Category(category);
            boolean status = false;
            if (isActive != null) {
                status = true;
            }
            Product product = new Product(productName, categoryInstance, Double.parseDouble(price), status, description, image);
            return productDao.create(product);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findAllProducts() throws ServiceException {
        try {
            ProductDao productDao = ProductDaoImpl.getInstance();
            return productDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
