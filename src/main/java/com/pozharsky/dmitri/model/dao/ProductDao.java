package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao extends AbstractDao<Product> {
    private static final Logger logger = LogManager.getLogger(ProductDao.class);
    private static final String PERCENT = "%";
    private static final String CREATE_PRODUCT_SQL = "INSERT INTO products (product_name, description, price, amount, status, image, time_create, category_id) VALUES (?,?,?,?,?,?,?,?);";
    private static final String FIND_ALL_PRODUCT_SQL = "SELECT id,product_name,price,amount,description,status,image,time_create,category_id FROM products;";
    private static final String FIND_PRODUCT_BY_CATEGORY_SQL = "SELECT id,product_name,price,amount,description,status,image,time_create,category_id FROM products WHERE category_id = ?;";
    private static final String FIND_PRODUCT_WITH_LIMIT_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products LIMIT ? OFFSET ?;";
    private static final String FIND_PRODUCT_BY_CATEGORY_WITH_LIMIT_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE category_id = ? LIMIT ? OFFSET ?;";
    private static final String FIND_PRODUCT_BY_CATEGORY_AND_STATUS_WITH_LIMIT_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE category_id = ? AND status = ? LIMIT ? OFFSET ?;";
    private static final String FIND_PRODUCT_BY_CATEGORY_AND_STATUS_WITH_LIMIT_ORDER_BY_PRICE_ASC_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE category_id = ? AND status = ? ORDER BY price ASC LIMIT ? OFFSET ?;";
    private static final String FIND_PRODUCT_BY_CATEGORY_AND_STATUS_WITH_LIMIT_ORDER_BY_PRICE_DESC_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE category_id = ? AND status = ? ORDER BY price DESC LIMIT ? OFFSET ?;";
    private static final String FIND_PRODUCT_BY_CATEGORY_AND_STATUS_WITH_LIMIT_ORDER_BY_CREATE_TIME_DESC_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE category_id = ? AND status = ? ORDER BY time_create DESC LIMIT ? OFFSET ?;";
    private static final String FIND_PRODUCT_BY_ID_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE id = ?";
    private static final String FIND_PRODUCT_BY_NAME_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE product_name LIKE ?";
    private static final String FIND_WITH_ORDER_BY_CREATE_TIME_DESC_AND_LIMIT_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products ORDER BY time_create DESC LIMIT ?";
    private static final String FIND_PRODUCT_BY_CATEGORY_ID_AND_STATUS_BETWEEN_PRICE_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE category_id = ? AND status = ? AND price BETWEEN ? AND ?;";
    private static final String FIND_PRODUCT_BY_CATEGORY_ID_AND_STATUS_AND_AMOUNT_GT_BETWEEN_PRICE_SQL = "SELECT id, product_name, price, amount, description, status, image, time_create, category_id FROM products WHERE category_id = ? AND status = ? AND amount > ? AND price BETWEEN ? AND ?;";
    private static final String COUNT_ALL_PRODUCT_SQL = "SELECT count(*) FROM products";
    private static final String COUNT_PRODUCT_BY_CATEGORY_SQL = "SELECT count(category_id) FROM products WHERE category_id = ?";
    private static final String COUNT_PRODUCT_BY_CATEGORY_AND_STATUS_SQL = "SELECT count(category_id) FROM products WHERE category_id = ? AND status = ?";
    private static final String COUNT_PRODUCT_BY_SEARCH_NAME_AND_STATUS_SQL = "SELECT count(product_name) FROM products WHERE product_name LIKE ? AND status = ?";
    private static final String MAX_PRODUCT_PRICE_BY_CATEGORY_ID_SQL = "SELECT max(price) FROM products WHERE category_id = ?";
    private static final String MIN_PRODUCT_PRICE_BY_CATEGORY_ID_SQL = "SELECT min(price) FROM products WHERE category_id = ?";
    private static final String INCREASE_AMOUNT_BY_ID_SQL = "UPDATE  products SET amount = amount + ? WHERE id = ?";
    private static final String DECREASE_AMOUNT_BY_ID_SQL = "UPDATE products SET amount = amount - ? WHERE id = ? AND amount >= ?";
    private static final String DELETE_PRODUCT_BY_CATEGORY_SQL = "DELETE FROM products WHERE category_id = ?";

    public boolean create(Product product) throws DaoException {
        try (PreparedStatement productPreparedStatement = connection.prepareStatement(CREATE_PRODUCT_SQL)) {
            productPreparedStatement.setString(1, product.getName());
            productPreparedStatement.setString(2, product.getDescription());
            productPreparedStatement.setBigDecimal(3, product.getPrice());
            productPreparedStatement.setInt(4, product.getAmount());
            productPreparedStatement.setBoolean(5, product.isStatus());
            productPreparedStatement.setBytes(6, product.getImage());
            productPreparedStatement.setTimestamp(7, Timestamp.valueOf(product.getCreatingTime()));
            productPreparedStatement.setLong(8, product.getCategoryId());
            int result = productPreparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findWithLimit(int limit, int offset) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_WITH_LIMIT_SQL)) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findByCategoryWithLimit(long categoryId, int limit, int offset) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_CATEGORY_WITH_LIMIT_SQL)) {
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findByCategoryAndStatusWithLimit(long categoryId, boolean status, int limit, int offset) throws DaoException {
        return findByCategoryAndStatusWithLimit(categoryId, status, limit, offset, FIND_PRODUCT_BY_CATEGORY_AND_STATUS_WITH_LIMIT_SQL);
    }

    public List<Product> findByCategoryAndStatusWithLimitOrderByPriceAsc(long categoryId, boolean status, int limit, int offset) throws DaoException {
        return findByCategoryAndStatusWithLimit(categoryId, status, limit, offset, FIND_PRODUCT_BY_CATEGORY_AND_STATUS_WITH_LIMIT_ORDER_BY_PRICE_ASC_SQL);
    }

    public List<Product> findByCategoryAndStatusWithLimitOrderByPriceDesc(long categoryId, boolean status, int limit, int offset) throws DaoException {
        return findByCategoryAndStatusWithLimit(categoryId, status, limit, offset, FIND_PRODUCT_BY_CATEGORY_AND_STATUS_WITH_LIMIT_ORDER_BY_PRICE_DESC_SQL);
    }

    public List<Product> findByCategoryAndStatusWithLimitOrderByTimeCreateDesc(long categoryId, boolean status, int limit, int offset) throws DaoException {
        return findByCategoryAndStatusWithLimit(categoryId, status, limit, offset, FIND_PRODUCT_BY_CATEGORY_AND_STATUS_WITH_LIMIT_ORDER_BY_CREATE_TIME_DESC_SQL);
    }

    public List<Product> findByCategory(long categoryId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_CATEGORY_SQL)) {
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findByProductName(String productName) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_NAME_SQL)) {
            preparedStatement.setString(1, PERCENT + productName + PERCENT);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findWithOrderByCreateTimeDescAndLimit(int limit) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_WITH_ORDER_BY_CREATE_TIME_DESC_AND_LIMIT_SQL)) {
            preparedStatement.setInt(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findProductByCategoryIdAndStatusBetweenPrice(long categoryId, boolean status, BigDecimal priceFrom, BigDecimal priceTo) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_CATEGORY_ID_AND_STATUS_BETWEEN_PRICE_SQL)) {
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setBoolean(2, status);
            preparedStatement.setBigDecimal(3, priceFrom);
            preparedStatement.setBigDecimal(4, priceTo);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Product> findProductByCategoryIdAndStatusAndAmountGtBetweenPrice(long categoryId, boolean status, int amount, BigDecimal priceFrom, BigDecimal priceTo) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_CATEGORY_ID_AND_STATUS_AND_AMOUNT_GT_BETWEEN_PRICE_SQL)) {
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setBoolean(2, status);
            preparedStatement.setInt(3, amount);
            preparedStatement.setBigDecimal(4, priceFrom);
            preparedStatement.setBigDecimal(5, priceTo);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }


    @Override
    public Optional<Product> findById(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID_SQL)) {
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
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Product> update(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID_SQL,
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
        throw new UnsupportedOperationException("Operation 'delete product' is unsupported");
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

    public void increaseAmountById(int amount, long productId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INCREASE_AMOUNT_BY_ID_SQL)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setLong(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public boolean decreaseAmountById(int amount, long productId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DECREASE_AMOUNT_BY_ID_SQL)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, amount);
            int resultUpdate = preparedStatement.executeUpdate();
            return resultUpdate > 0;
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

    public int countProductByCategory(long categoryId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_PRODUCT_BY_CATEGORY_SQL)) {
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public int countProductByCategoryAndStatus(long categoryId, boolean status) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_PRODUCT_BY_CATEGORY_AND_STATUS_SQL)) {
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setBoolean(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public int countProductBySearchNameAndStatus(String searchProduct, boolean status) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_PRODUCT_BY_SEARCH_NAME_AND_STATUS_SQL)) {
            preparedStatement.setString(1, PERCENT + searchProduct + PERCENT);
            preparedStatement.setBoolean(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public BigDecimal maxProductPriceByCategoryId(long categoryId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MAX_PRODUCT_PRICE_BY_CATEGORY_ID_SQL)) {
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBigDecimal(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public BigDecimal minProductPriceByCategoryId(long categoryId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MIN_PRODUCT_PRICE_BY_CATEGORY_ID_SQL)) {
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBigDecimal(1);
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
        product.setAmount(resultSet.getInt(ColumnName.AMOUNT));
        product.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
        product.setStatus(resultSet.getBoolean(ColumnName.STATUS));
        product.setImage(resultSet.getBytes(ColumnName.IMAGE));
        product.setCreatingTime(resultSet.getTimestamp(ColumnName.TIME_CREATE).toLocalDateTime());
        product.setCategoryId(resultSet.getLong(ColumnName.CATEGORY_ID));
        return product;
    }

    private List<Product> createListProductFromResultSet(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = createProductFromResultSet(resultSet);
            products.add(product);
        }
        return products;
    }

    private void updateProductInResultSet(ResultSet resultSet, Product product) throws SQLException {
        resultSet.updateString(ColumnName.PRODUCT_NAME, product.getName());
        resultSet.updateString(ColumnName.DESCRIPTION, product.getDescription());
        resultSet.updateBigDecimal(ColumnName.PRICE, product.getPrice());
        resultSet.updateInt(ColumnName.AMOUNT, product.getAmount());
        resultSet.updateBoolean(ColumnName.STATUS, product.isStatus());
        resultSet.updateTimestamp(ColumnName.TIME_CREATE, Timestamp.valueOf(product.getCreatingTime()));
        resultSet.updateLong(ColumnName.CATEGORY_ID, product.getCategoryId());
        byte[] image = product.getImage();
        if (image.length != 0) {
            resultSet.updateBytes(ColumnName.IMAGE, product.getImage());
        }
    }

    private List<Product> findByCategoryAndStatusWithLimit(long categoryId, boolean status, int limit, int offset, String sqlQuery) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setBoolean(2, status);
            preparedStatement.setInt(3, limit);
            preparedStatement.setInt(4, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createListProductFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
