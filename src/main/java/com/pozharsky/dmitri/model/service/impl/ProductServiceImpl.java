package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.creator.ProductCreator;
import com.pozharsky.dmitri.model.dao.CategoryDao;
import com.pozharsky.dmitri.model.dao.ProductDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.validator.ProductValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private static final int DEFAULT_PER_PAGE = 5;
    private static final int DEFAULT_PAGE = 1;
    private static final ProductServiceImpl instance = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createProduct(Map<String, String> productForm, Part part) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isCreate = false;
            if (ProductValidator.isValidProductForm(productForm)) {
                ProductDao productDao = new ProductDao();
                CategoryDao categoryDao = new CategoryDao();
                transactionManager.initTransaction(productDao, categoryDao);
                String categoryName = productForm.get(RequestParameter.CATEGORY);
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
                    Product product = ProductCreator.createProduct(productForm, part, categoryId);
                    isCreate = productDao.create(product);
                }
                transactionManager.commit();
            }
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
    public List<Product> findProductByCategory(String categoryId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            long id = Long.parseLong(categoryId);
            return productDao.findByCategory(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
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
    public int defineAmountProduct() throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.countAllProduct();
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
            int limit = perPage != null ? Integer.parseInt(perPage) : DEFAULT_PER_PAGE;
            int numberPage = page != null ? Integer.parseInt(page) : DEFAULT_PAGE;
            int offset = (numberPage - 1) * limit;
            return productDao.findByLimitAndOffset(limit, offset);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }
}
