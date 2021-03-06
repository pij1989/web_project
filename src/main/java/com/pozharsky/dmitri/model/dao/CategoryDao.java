package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class CategoryDao is used to interact with categories table in the database.
 *
 * @author Dmitri Pozharsky
 */
public class CategoryDao extends AbstractDao<Category> {
    private static final Logger logger = LogManager.getLogger(ProductDao.class);
    private static final String CREATE_CATEGORY_SQL = "INSERT INTO categories (category_name) VALUES (?);";
    private static final String FIND_CATEGORY_BY_NAME_SQL = "SELECT id, category_name FROM categories WHERE category_name = ?;";
    private static final String FIND_CATEGORY_BY_ID_SQL = "SELECT id, category_name FROM categories WHERE id = ?;";
    private static final String FIND_ALL_CATEGORY_SQL = "SELECT id, category_name FROM categories;";
    private static final String DELETE_CATEGORY_BY_ID_SQL = "DELETE FROM categories WHERE id = ?;";

    /**
     * Create new category in database.
     *
     * @param category Category object will be created in the database.
     * @return Not empty Optional with id of creating category if it has been created, otherwise Optional.empty().
     * @throws DaoException if the database throws SQLException.
     */
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

    /**
     * Find category by name.
     *
     * @param name String object of category's name.
     * @return Not empty Optional entity object if it was found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    public Optional<Category> findCategoryByName(String name) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_NAME_SQL)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category category = createCategoryFromResultSet(resultSet);
                return Optional.of(category);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Category> findAll() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CATEGORY_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                Category category = createCategoryFromResultSet(resultSet);
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Category> update(Category category) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_ID_SQL,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            preparedStatement.setLong(1, category.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category findCategory = createCategoryFromResultSet(resultSet);
                resultSet.updateString(ColumnName.CATEGORY_NAME, category.getName());
                resultSet.updateRow();
                return Optional.of(findCategory);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATEGORY_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    private Category createCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong(ColumnName.ID));
        category.setName(resultSet.getString(ColumnName.CATEGORY_NAME));
        return category;
    }
}
