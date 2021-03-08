package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.creator.ProductCreator;
import com.pozharsky.dmitri.model.dao.ProductDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
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
                transactionManager.init(productDao);
                Product product = ProductCreator.createProduct(productForm, part);
                isCreate = productDao.create(product);
            }
            return isCreate;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> findProductByCategory(long categoryId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.findByCategory(categoryId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public Optional<Product> findProductById(long productId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.findById(productId);
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
    public Optional<Product> updateProduct(Map<String, String> productForm, String productId, Part part) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            Optional<Product> optionalProduct = Optional.empty();
            if (ProductValidator.isValidProductForm(productForm)) {
                ProductDao productDao = new ProductDao();
                transactionManager.initTransaction(productDao);
                long id = Long.parseLong(productId);
                Product product = ProductCreator.createProduct(productForm, part);
                product.setId(id);
                optionalProduct = productDao.update(product);
                transactionManager.commit();
            }
            return optionalProduct;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
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
    public int defineAmountProductByCategory(long categoryId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.countProductByCategory(categoryId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> findProductsByPerPage(String page, String perPage) throws ServiceException {
        int limit;
        int numberPage;
        try {
            limit = perPage != null ? Integer.parseInt(perPage) : DEFAULT_PER_PAGE;
            numberPage = page != null ? Integer.parseInt(page) : DEFAULT_PAGE;
        } catch (NumberFormatException e) {
            logger.error(e);
            limit = DEFAULT_PER_PAGE;
            numberPage = DEFAULT_PAGE;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            int offset = (numberPage - 1) * limit;
            return productDao.findByLimitAndOffset(limit, offset);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> findProductsByCategoryAndPerPage(long categoryId, String page, String perPage) throws ServiceException {
        int limit;
        int numberPage;
        try {
            limit = perPage != null ? Integer.parseInt(perPage) : DEFAULT_PER_PAGE;
            numberPage = page != null ? Integer.parseInt(page) : DEFAULT_PAGE;
        } catch (NumberFormatException e) {
            logger.error(e);
            limit = DEFAULT_PER_PAGE;
            numberPage = DEFAULT_PAGE;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            int offset = (numberPage - 1) * limit;
            return productDao.findByCategoryAndLimitAndOffset(categoryId, limit, offset);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> searchProduct(String searchProduct) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.findByProductName(searchProduct);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> findLastAddProduct(int limit) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.findWithOrderByCreateTimeDescAndLimit(limit);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }
}
