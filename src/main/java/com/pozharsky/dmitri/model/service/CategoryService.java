package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides actions on category.
 *
 * @author Dmitri Pozharsky
 */
public interface CategoryService {
    boolean createCategory(Category category) throws ServiceException;

    Optional<Category> findCategoryById(long categoryId) throws ServiceException;

    List<Category> findAllCategory() throws ServiceException;

    Optional<Category> updateCategory(String categoryId, String name) throws ServiceException;

    boolean deleteCategoryAndProducts(String categoryId) throws ServiceException;
}
