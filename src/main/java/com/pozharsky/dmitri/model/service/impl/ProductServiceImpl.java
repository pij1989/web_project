package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.CategoryDao;
import com.pozharsky.dmitri.model.dao.ProductDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.util.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private static final ProductServiceImpl instance = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createProduct(String productName, String categoryName, String price, String isActive,
                                 String description, byte[] image, String creatingTime) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isCreate = false;
            ProductDao productDao = new ProductDao();
            CategoryDao categoryDao = new CategoryDao();
            transactionManager.initTransaction(productDao, categoryDao);
            Optional<Category> optionalCategory = categoryDao.findCategoryByName(categoryName);
            Optional<Long> optionalCategoryId;
            if (optionalCategory.isPresent()) {
                optionalCategoryId = optionalCategory.map(Category::getId);
            } else {
                Category categoryInstance = new Category(categoryName);
                optionalCategoryId = categoryDao.create(categoryInstance);
            }
            if (optionalCategoryId.isPresent()) {
                long categoryId = optionalCategoryId.get();
                boolean status = Boolean.parseBoolean(isActive);
                BigDecimal productPrice = BigDecimal.valueOf(Double.parseDouble(price));
                LocalDateTime productCreatingTime = LocalDateTime.parse(creatingTime);
                Product product = new Product(productName, productPrice, status, description, image, productCreatingTime, categoryId);
                isCreate = productDao.create(product);
            }
            transactionManager.commit();
            return isCreate;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public List<Product> findAllProducts() throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public int defineAmountProductPage(String perPage) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            int count = productDao.countAllProduct();
            return PaginationUtil.defineAmountPage(count,Integer.parseInt(perPage));
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> findProductsByPerPage(String page, String perPage) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            int limit = Integer.parseInt(perPage);
            int offset = (Integer.parseInt(page) - 1) * limit;
            return productDao.findByLimitAndOffset(limit,offset);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }
}
