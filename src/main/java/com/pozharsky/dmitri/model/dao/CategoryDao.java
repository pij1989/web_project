package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class CategoryDao extends AbstractDao<Category> {
    private static final Logger logger = LogManager.getLogger(ProductDao.class);
    private static final String CREATE_CATEGORY_SQL = "INSERT INTO categories (category_name) VALUES (?);";
    private static final String FIND_CATEGORY_BY_NAME = "SELECT id, category_name FROM categories WHERE category_name = ?;";

    public Optional<Long> create(Category category) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CATEGORY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            ResultSet categoryKeys = preparedStatement.getGeneratedKeys();
            if (categoryKeys.next()) {
                long categoryId = categoryKeys.getLong(1);
                return Optional.of(categoryId);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public Optional<Category> findCategoryByName(String name) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category category = createCategoryFromResultSet(resultSet);
                return Optional.of(category);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Category> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Category> update(Category entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Category entity) throws DaoException {
        return false;
    }

    private Category createCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong(ColumnName.ID));
        category.setName(resultSet.getString(ColumnName.CATEGORY_NAME));
        return category;
    }
}
