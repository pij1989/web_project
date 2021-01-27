package com.pozharsky.dmitri.model.dao.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.connector.ConnectionPool;
import com.pozharsky.dmitri.model.dao.ColumnName;
import com.pozharsky.dmitri.model.dao.UserDao;
import com.pozharsky.dmitri.model.entity.RoleType;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.Token;
import com.pozharsky.dmitri.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final String CREATE_USER_SQL = "INSERT INTO users (first_name,last_name,username,email,password,role_id,status_id) VALUES (?,?,?,?,?,?,?);";
    private static final String FIND_ROLE_ID_BY_NAME_SQL = "SELECT id FROM roles WHERE role_name = ?;";
    private static final String FIND_STATUS_ID_BY_NAME_SQL = "SELECT id FROM status WHERE status_name = ?;";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT u.id,u.first_name,u.last_name,u.username,u.email,r.role_name,s.status_name FROM users AS u INNER JOIN roles AS r ON u.role_id = r.id INNER JOIN status AS s ON u.status_id = s.id WHERE u.email = ?";
    private static final String FIND_PASSWORD_BY_EMAIL_SQL = "SELECT password FROM users WHERE email = ?";
    private static final String CREATE_TOKEN_SQL = "INSERT INTO tokens (token_value, time_create, time_expire, user_id) VALUES (?,?,?,?)";

    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean create(User user, String password) throws DaoException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        boolean isCreateUser = false;
        try (PreparedStatement userPreparedStatement = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement rolePreparedStatement = connection.prepareStatement(FIND_ROLE_ID_BY_NAME_SQL);
             PreparedStatement statusPreparedStatement = connection.prepareStatement(FIND_STATUS_ID_BY_NAME_SQL);
             PreparedStatement tokenPreparedStatement = connection.prepareStatement(CREATE_TOKEN_SQL)) {
            connection.setAutoCommit(false);
            rolePreparedStatement.setString(1, user.getRoleType().toString());
            statusPreparedStatement.setString(1, user.getStatusType().toString());
            ResultSet roleKeys = rolePreparedStatement.executeQuery();
            ResultSet statusKeys = statusPreparedStatement.executeQuery();
            userPreparedStatement.setString(1, user.getFirstName());
            userPreparedStatement.setString(2, user.getLastName());
            userPreparedStatement.setString(3, user.getUsername());
            userPreparedStatement.setString(4, user.getEmail());
            userPreparedStatement.setString(5, password);
            if (roleKeys.next()) {
                int roleId = roleKeys.getInt(ColumnName.ID);
                userPreparedStatement.setInt(6, roleId);
            }
            if (statusKeys.next()) {
                int statusId = statusKeys.getInt(ColumnName.ID);
                userPreparedStatement.setInt(7, statusId);
            }
            int resultCreateUser = userPreparedStatement.executeUpdate();
            ResultSet userKeys = userPreparedStatement.getGeneratedKeys();
            userKeys.next();
            int userId = userKeys.getInt(1);
            logger.debug("USER_ID: " + userId);
            Token token = user.getToken();
            tokenPreparedStatement.setString(1, token.getTokenValue());
            tokenPreparedStatement.setObject(2, token.getTimeCreate(), JDBCType.DATE);
            tokenPreparedStatement.setObject(3, token.getTimeExpire(), JDBCType.DATE);
            tokenPreparedStatement.setInt(4, userId);
            int resultCreateToken = tokenPreparedStatement.executeUpdate();
            if (resultCreateUser == 1 && resultCreateToken == 1) {
                isCreateUser = true;
            }
            connection.commit();
            return isCreateUser;
        } catch (SQLException e) {
            rollback(connection);
            logger.error("Impossible create user in database");
            throw new DaoException("Database problem when create user: " + e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public String findPasswordByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PASSWORD_BY_EMAIL_SQL)) {
            String password = "";
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(ColumnName.PASSWORD);
            }
            return password;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public User findById(long id) {
        return new User();
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public User update(User entity) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(User entity) throws DaoException {
        return false;
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(ColumnName.ID));
        user.setFirstName(resultSet.getString(ColumnName.FIRST_NAME));
        user.setLastName(resultSet.getString(ColumnName.LAST_NAME));
        user.setUsername(resultSet.getString(ColumnName.USERNAME));
        user.setEmail(resultSet.getString(ColumnName.EMAIL));
        user.setRoleType(RoleType.valueOf(resultSet.getString(ColumnName.ROLE_NAME)));
        user.setStatusType(StatusType.valueOf(resultSet.getString(ColumnName.STATUS_NAME)));
        return user;
    }
}
