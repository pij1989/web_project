package com.pozharsky.dmitri.model.dao.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.connector.ConnectionPool;
import com.pozharsky.dmitri.model.dao.ColumnName;
import com.pozharsky.dmitri.model.dao.ProductDao;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    private static final Logger logger = LogManager.getLogger(ProductDaoImpl.class);
    private static final String CREATE_PRODUCT_SQL = "INSERT INTO products (product_name, description, price, status, image, category_id) VALUES (?,?,?,?,?,?);";
    private static final String CREATE_CATEGORY_SQL = "INSERT INTO categories (category_name) VALUES (?);";
    private static final String FIND_ALL_PRODUCT_SQL = "SELECT id,product_name,price,description,status,image FROM products;";


    private static final ProductDaoImpl instance = new ProductDaoImpl();

    private ProductDaoImpl() {
    }

    public static ProductDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean create(Product product) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement productPreparedStatement = connection.prepareStatement(CREATE_PRODUCT_SQL);
             PreparedStatement categoryPreparedStatement = connection.prepareStatement(CREATE_CATEGORY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            Category category = product.getCategory();
            categoryPreparedStatement.setString(1, category.getName());
            categoryPreparedStatement.executeUpdate();
            ResultSet categoryKeys = categoryPreparedStatement.getGeneratedKeys();
            int result = 0;
            if (categoryKeys.next()) {
                long categoryKey = categoryKeys.getLong(1);
                productPreparedStatement.setString(1, product.getName());
                productPreparedStatement.setString(2, product.getDescription());
                productPreparedStatement.setDouble(3, product.getPrice());
                productPreparedStatement.setBoolean(4, product.isStatus());
                productPreparedStatement.setBytes(5, product.getImage());
                productPreparedStatement.setLong(6, categoryKey);
                result = productPreparedStatement.executeUpdate();
            }
            return result > 0;
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
            throw new DaoException(e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<Product> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCT_SQL)) {
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
    public Optional<Product> update(Product entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Product entity) throws DaoException {
        return false;
    }

    private Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(ColumnName.ID));
        product.setName(resultSet.getString(ColumnName.PRODUCT_NAME));
        product.setPrice(resultSet.getDouble(ColumnName.PRICE));
        product.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
        product.setStatus(resultSet.getBoolean(ColumnName.STATUS));
        product.setImage(resultSet.getBytes(ColumnName.IMAGE));
        return product;
    }
}
