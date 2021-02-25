package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao extends AbstractDao<Product> {
    private static final Logger logger = LogManager.getLogger(ProductDao.class);
    private static final String CREATE_PRODUCT_SQL = "INSERT INTO products (product_name, description, price, status, image, time_create, category_id) VALUES (?,?,?,?,?,?,?);";
    private static final String FIND_ALL_PRODUCT_SQL = "SELECT id,product_name,price,description,status,image,time_create,category_id FROM products;";
    private static final String FIND_PRODUCT_BY_CATEGORY_SQL = "SELECT id,product_name,price,description,status,image,time_create,category_id FROM products WHERE category_id = ?;";
    private static final String FIND_PRODUCT_BY_LIMIT_AND_OFFSET_SQL = "SELECT id, product_name, price, description, status, image, time_create, category_id FROM products LIMIT ? OFFSET ?;";
    private static final String FIND_PRODUCT_BY_ID = "SELECT id, product_name, price, description, status, image, time_create, category_id FROM products WHERE id = ?";
    private static final String COUNT_ALL_PRODUCT_SQL = "SELECT count(*) FROM products";
    private static final String DELETE_PRODUCT_BY_CATEGORY_SQL = "DELETE FROM products WHERE category_id = ?";

    public boolean create(Product product) throws DaoException {
        try (PreparedStatement productPreparedStatement = connection.prepareStatement(CREATE_PRODUCT_SQL)) {
            productPreparedStatement.setString(1, product.getName());
            productPreparedStatement.setString(2, product.getDescription());
            productPreparedStatement.setBigDecimal(3, product.getPrice());
            productPreparedStatement.setBoolean(4, product.isStatus());
            productPreparedStatement.setBytes(5, product.getImage());
            productPreparedStatement.setTimestamp(6, Timestamp.valueOf(product.getCreatingTime()));
            productPreparedStatement.setLong(7, product.getCategoryId());
            int result = productPreparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findByLimitAndOffset(int limit, int offset) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_LIMIT_AND_OFFSET_SQL)) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = createProductFromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findByCategory(long categoryId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_CATEGORY_SQL)) {
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = createProductFromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Product> findById(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = createProductFromResultSet(resultSet);
                return Optional.of(product);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Product> findAll() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCT_SQL)) {
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = createProductFromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Product> update(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            preparedStatement.setLong(1, product.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product findProduct = createProductFromResultSet(resultSet);
                updateProductInResultSet(resultSet, product);
                resultSet.updateRow();
                return Optional.of(findProduct);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Product entity) throws DaoException {
        return false;
    }

    public boolean deleteByCategory(long categoryId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_CATEGORY_SQL)) {
            preparedStatement.setLong(1, categoryId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public int countAllProduct() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_PRODUCT_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }


    private Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(ColumnName.ID));
        product.setName(resultSet.getString(ColumnName.PRODUCT_NAME));
        product.setPrice(resultSet.getBigDecimal(ColumnName.PRICE));
        product.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
        product.setStatus(resultSet.getBoolean(ColumnName.STATUS));
        product.setImage(resultSet.getBytes(ColumnName.IMAGE));
        product.setCreatingTime(resultSet.getTimestamp(ColumnName.TIME_CREATE).toLocalDateTime());
        product.setCategoryId(resultSet.getLong(ColumnName.CATEGORY_ID));
        return product;
    }

    private void updateProductInResultSet(ResultSet resultSet, Product product) throws SQLException {
        resultSet.updateString(ColumnName.PRODUCT_NAME, product.getName());
        resultSet.updateString(ColumnName.DESCRIPTION, product.getDescription());
        resultSet.updateBigDecimal(ColumnName.PRICE, product.getPrice());
        resultSet.updateBoolean(ColumnName.STATUS, product.isStatus());
        resultSet.updateTimestamp(ColumnName.TIME_CREATE, Timestamp.valueOf(product.getCreatingTime()));
        resultSet.updateLong(ColumnName.CATEGORY_ID, product.getCategoryId());
        byte[] image = product.getImage();
        if (image.length != 0) {
            resultSet.updateBytes(ColumnName.IMAGE, product.getImage());
        }
    }
}
