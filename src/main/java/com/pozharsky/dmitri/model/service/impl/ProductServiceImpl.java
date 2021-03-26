package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.creator.ProductCreator;
import com.pozharsky.dmitri.model.dao.ProductDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.SortType;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.validator.PageValidator;
import com.pozharsky.dmitri.validator.ProductValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

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
    public int defineAmountActiveProductByCategory(long categoryId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.countProductByCategoryAndStatus(categoryId, true);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public int defineAmountActiveSearchProduct(String searchProduct) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.countProductBySearchNameAndStatus(searchProduct, true);
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
        if (PageValidator.isValidPage(page, perPage)) {
            limit = Integer.parseInt(perPage);
            numberPage = Integer.parseInt(page);
        } else {
            limit = DEFAULT_PER_PAGE;
            numberPage = DEFAULT_PAGE;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            int offset = (numberPage - 1) * limit;
            return productDao.findWithLimit(limit, offset);
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
        if (PageValidator.isValidPage(page, perPage)) {
            limit = Integer.parseInt(perPage);
            numberPage = Integer.parseInt(page);
        } else {
            limit = DEFAULT_PER_PAGE;
            numberPage = DEFAULT_PAGE;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            int offset = (numberPage - 1) * limit;
            return productDao.findByCategoryWithLimit(categoryId, limit, offset);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> findActiveProductsByCategoryAndPerPage(long categoryId, String page, String perPage, String sort) throws ServiceException {
        int limit;
        int numberPage;
        if (PageValidator.isValidPage(page, perPage)) {
            limit = Integer.parseInt(perPage);
            numberPage = Integer.parseInt(page);
        } else {
            limit = DEFAULT_PER_PAGE;
            numberPage = DEFAULT_PAGE;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            int offset = (numberPage - 1) * limit;
            if (sort == null) {
                return productDao.findByCategoryAndStatusWithLimit(categoryId, true, limit, offset);
            } else {
                return findSortedActiveProduct(productDao, categoryId, limit, offset, sort);
            }
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
    public List<Product> searchActiveProduct(String searchProduct) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.findByProductNameAndStatus(searchProduct, true);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> filterActiveProduct(long categoryId, Map<String, String> filterForm, String sort) throws ServiceException {
        List<Product> products = new ArrayList<>();
        if (!ProductValidator.isValidFilterProductForm(filterForm)) {
            return products;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            String stringPriceFrom = filterForm.get(PRICE_FROM);
            String stringPriceTo = filterForm.get(PRICE_TO);
            BigDecimal priceFrom = definePriceFrom(categoryId, productDao, stringPriceFrom);
            BigDecimal priceTo = definePriceTo(categoryId, productDao, stringPriceTo);
            boolean inStock = Boolean.parseBoolean(filterForm.get(IN_STOCK));
            if (inStock) {
                products = productDao.findProductByCategoryIdAndStatusAndAmountGtBetweenPrice(categoryId, true, 0, priceFrom, priceTo);
            } else {
                products = productDao.findProductByCategoryIdAndStatusBetweenPrice(categoryId, true, priceFrom, priceTo);
            }
            if (sort != null) {
                products = sortProducts(products, sort);
            }
            return products;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Product> findLastAddActiveProduct(int limit) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ProductDao productDao = new ProductDao();
            transactionManager.init(productDao);
            return productDao.findWithOrderByCreateTimeDescAndStatusWithLimit(limit, true);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    private List<Product> findSortedActiveProduct(ProductDao productDao, long categoryId, int limit,
                                                  int offset, String sort) throws DaoException {
        try {
            SortType sortType = SortType.valueOf(sort.toUpperCase());
            switch (sortType) {
                case NEW: {
                    return productDao.findByCategoryAndStatusWithLimitOrderByTimeCreateDesc(categoryId, true, limit, offset);
                }
                case CHEAP: {
                    return productDao.findByCategoryAndStatusWithLimitOrderByPriceAsc(categoryId, true, limit, offset);
                }
                case EXPENSIVE: {
                    return productDao.findByCategoryAndStatusWithLimitOrderByPriceDesc(categoryId, true, limit, offset);
                }
                default: {
                    return productDao.findByCategoryAndStatusWithLimit(categoryId, true, limit, offset);
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error(e);
            return productDao.findByCategoryAndStatusWithLimit(categoryId, true, limit, offset);
        }
    }

    private List<Product> sortProducts(List<Product> products, String sort) {
        try {
            SortType sortType = SortType.valueOf(sort.toUpperCase());
            switch (sortType) {
                case NEW: {
                    return products.stream()
                            .sorted(Comparator.comparing(Product::getCreatingTime).reversed())
                            .collect(Collectors.toList());
                }
                case CHEAP: {
                    return products.stream()
                            .sorted(Comparator.comparing(Product::getPrice))
                            .collect(Collectors.toList());
                }
                case EXPENSIVE: {
                    return products.stream()
                            .sorted(Comparator.comparing(Product::getPrice).reversed())
                            .collect(Collectors.toList());
                }
                default: {
                    return products;
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error(e);
            return products;
        }
    }

    private BigDecimal definePriceFrom(long categoryId, ProductDao productDao, String stringPriceFrom) throws DaoException {
        return stringPriceFrom.isBlank() ? productDao.minProductPriceByCategoryId(categoryId) : new BigDecimal(stringPriceFrom);
    }

    private BigDecimal definePriceTo(long categoryId, ProductDao productDao, String stringPriceTo) throws DaoException {
        return stringPriceTo.isBlank() ? productDao.maxProductPriceByCategoryId(categoryId) : new BigDecimal(stringPriceTo);
    }
}
