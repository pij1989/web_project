package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.CategoryDao;
import com.pozharsky.dmitri.model.dao.ProductDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.service.CategoryService;
import com.pozharsky.dmitri.validator.CategoryValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * CategoryService implementation.
 *
 * @author Dmitri Pozharsky
 */
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private static final CategoryServiceImpl instance = new CategoryServiceImpl();

    private CategoryServiceImpl() {
    }

    public static CategoryServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createCategory(Category category) throws ServiceException {
        if (!CategoryValidator.isCategoryName(category.getName())) {
            return false;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isCreate = false;
            CategoryDao categoryDao = new CategoryDao();
            transactionManager.init(categoryDao);
            Optional<Long> optionalId = categoryDao.create(category);
            if (optionalId.isPresent()) {
                long categoryId = optionalId.get();
                category.setId(categoryId);
                isCreate = true;
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
    public Optional<Category> findCategoryById(long categoryId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            CategoryDao categoryDao = new CategoryDao();
            transactionManager.init(categoryDao);
            return categoryDao.findById(categoryId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Category> findAllCategory() throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            CategoryDao categoryDao = new CategoryDao();
            transactionManager.init(categoryDao);
            return categoryDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public Optional<Category> updateCategory(String categoryId, String name) throws ServiceException {
        if (!CategoryValidator.isCategoryName(name)) {
            return Optional.empty();
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            CategoryDao categoryDao = new CategoryDao();
            transactionManager.initTransaction(categoryDao);
            long id = Long.parseLong(categoryId);
            Category category = new Category(id, name);
            Optional<Category> oldCategory = categoryDao.update(category);
            transactionManager.commit();
            return oldCategory;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public boolean deleteCategoryAndProducts(String categoryId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isDelete = false;
            CategoryDao categoryDao = new CategoryDao();
            ProductDao productDao = new ProductDao();
            transactionManager.initTransaction(categoryDao, productDao);
            long id = Long.parseLong(categoryId);
            productDao.deleteById(id);
            isDelete = categoryDao.deleteById(id);
            transactionManager.commit();
            return isDelete;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }
}
