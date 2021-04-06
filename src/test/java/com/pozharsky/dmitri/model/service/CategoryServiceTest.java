package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.service.impl.CategoryServiceImpl;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class CategoryServiceTest {
    CategoryService categoryService;

    @BeforeTest
    public void setUp() {
        categoryService = CategoryServiceImpl.getInstance();
    }

    @Test
    public void testFindCategoryByIdCorrect() {
        long id = 1L;
        Category category = new Category();
        category.setId(id);
        category.setName("Processor");
        Optional<Category> expected = Optional.of(category);
        Optional<Category> actual = Optional.empty();
        try {
            actual = categoryService.findCategoryById(id);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCategoryByIdIncorrect() {
        long id = 1000L;
        Optional<Category> expected = Optional.empty();
        Optional<Category> actual = Optional.empty();
        try {
            actual = categoryService.findCategoryById(id);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
        assertEquals(actual, expected);
    }

    @AfterTest
    public void tearDown() {
        categoryService = null;
    }
}