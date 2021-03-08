package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {
    private static final Logger logger = LogManager.getLogger(UserDao.class);
    private static final String CREATE_USER_SQL = "INSERT INTO users (first_name,last_name,username,email,password,role_id,status_id) VALUES (?,?,?,?,?,?,?);";
    private static final String FIND_ROLE_ID_BY_NAME_SQL = "SELECT id FROM roles WHERE role_name = ?;";
    private static final String FIND_STATUS_ID_BY_NAME_SQL = "SELECT id FROM status WHERE status_name = ?;";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT u.id,u.first_name,u.last_name,u.username,u.email,r.role_name,s.status_name FROM users AS u INNER JOIN roles AS r ON u.role_id = r.id INNER JOIN status AS s ON u.status_id = s.id WHERE u.email = ?";
    private static final String FIND_PASSWORD_BY_EMAIL_SQL = "SELECT password FROM users WHERE email = ?";
    private static final String FIND_USER_BY_ID_SQL = "SELECT u.id,u.first_name,u.last_name,u.username,u.email,r.role_name,s.status_name FROM users AS u INNER JOIN roles AS r ON u.role_id = r.id INNER JOIN status AS s ON u.status_id = s.id WHERE u.id = ?";
    private static final String FIND_ALL_USERS_SQL = "SELECT u.id,u.first_name,u.last_name,u.username,u.email,r.role_name,s.status_name FROM users AS u INNER JOIN roles AS r ON u.role_id = r.id INNER JOIN status AS s ON u.status_id = s.id";
    private static final String UPDATE_USER_SQL = "UPDATE users SET first_name = ?,last_name = ?,username = ?,email = ?,role_id = ?,status_id = ? WHERE id = ?";
    private static final String UPDATE_USER_STATUS_BY_ID_SQL = "UPDATE users SET status_id = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD_BY_EMAIL_SQL = "UPDATE users SET password = ? WHERE email = ?";

    public Optional<Long> create(User user, String password) throws DaoException {
        try (PreparedStatement userPreparedStatement = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement rolePreparedStatement = connection.prepareStatement(FIND_ROLE_ID_BY_NAME_SQL);
             PreparedStatement statusPreparedStatement = connection.prepareStatement(FIND_STATUS_ID_BY_NAME_SQL)) {
            userPreparedStatement.setString(1, user.getFirstName());
            userPreparedStatement.setString(2, user.getLastName());
            userPreparedStatement.setString(3, user.getUsername());
            userPreparedStatement.setString(4, user.getEmail());
            userPreparedStatement.setString(5, password);
            long roleId = findRoleId(rolePreparedStatement, user.getRoleType());
            userPreparedStatement.setLong(6, roleId);
            long statusId = findStatusId(statusPreparedStatement, user.getStatusType());
            userPreparedStatement.setLong(7, statusId);
            int resultCreateUser = userPreparedStatement.executeUpdate();
            ResultSet userKeys = userPreparedStatement.getGeneratedKeys();
            if (userKeys.next()) {
                long userId = userKeys.getLong(1);
                logger.debug("USER_ID: " + userId);
                return Optional.of(userId);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Impossible create user in database");
            throw new DaoException(e);
        }
    }

    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {
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

    public String findPasswordByEmail(String email) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PASSWORD_BY_EMAIL_SQL)) {
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

    public boolean updateUserStatusById(long id, User.StatusType status) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_STATUS_BY_ID_SQL);
             PreparedStatement statusPreparedStatement = connection.prepareStatement(FIND_STATUS_ID_BY_NAME_SQL)) {
            long statusId = findStatusId(statusPreparedStatement, status);
            preparedStatement.setLong(1, statusId);
            preparedStatement.setLong(2, id);
            int resultUpdateStatus = preparedStatement.executeUpdate();
            return resultUpdateStatus == 1;
        } catch (SQLException e) {
            logger.error("Impossible update user status: " + e);
            throw new DaoException(e);
        }
    }

    public boolean updatePasswordByEmail(String email, String password) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_BY_EMAIL_SQL)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            int resultUpdatePassword = preparedStatement.executeUpdate();
            return resultUpdatePassword == 1;
        } catch (SQLException e) {
            logger.error("Impossible update user password: " + e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Impossible find user by id: " + e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS_SQL)) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Impossible find all users: " + e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        User findUser = null;
        try (PreparedStatement findUserPreparedStatement = connection.prepareStatement(FIND_USER_BY_ID_SQL);
             PreparedStatement updateUserPreparedStatement = connection.prepareStatement(UPDATE_USER_SQL);
             PreparedStatement rolePreparedStatement = connection.prepareStatement(FIND_ROLE_ID_BY_NAME_SQL);
             PreparedStatement statusPreparedStatement = connection.prepareStatement(FIND_STATUS_ID_BY_NAME_SQL)) {
            long userId = user.getId();
            findUserPreparedStatement.setLong(1, userId);
            ResultSet resultSet = findUserPreparedStatement.executeQuery();
            if (resultSet.next()) {
                findUser = createUserFromResultSet(resultSet);
            }
            long roleId = findRoleId(rolePreparedStatement, user.getRoleType());
            long statusId = findStatusId(statusPreparedStatement, user.getStatusType());
            updateUserPreparedStatement.setString(1, user.getFirstName());
            updateUserPreparedStatement.setString(2, user.getLastName());
            updateUserPreparedStatement.setString(3, user.getUsername());
            updateUserPreparedStatement.setString(4, user.getEmail());
            updateUserPreparedStatement.setLong(5, roleId);
            updateUserPreparedStatement.setLong(6, statusId);
            updateUserPreparedStatement.setLong(7, userId);
            updateUserPreparedStatement.executeUpdate();
            return Optional.ofNullable(findUser);
        } catch (SQLException e) {
            logger.error("Impossible update user: " + e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(User entity) throws DaoException {
        return false;
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(ColumnName.ID));
        user.setFirstName(resultSet.getString(ColumnName.FIRST_NAME));
        user.setLastName(resultSet.getString(ColumnName.LAST_NAME));
        user.setUsername(resultSet.getString(ColumnName.USERNAME));
        user.setEmail(resultSet.getString(ColumnName.EMAIL));
        user.setRoleType(User.RoleType.valueOf(resultSet.getString(ColumnName.ROLE_NAME)));
        user.setStatusType(User.StatusType.valueOf(resultSet.getString(ColumnName.STATUS_NAME)));
        return user;
    }

    private long findRoleId(PreparedStatement rolePreparedStatement, User.RoleType roleType) throws SQLException {
        rolePreparedStatement.setString(1, roleType.toString());
        ResultSet roleKeys = rolePreparedStatement.executeQuery();
        roleKeys.next();
        return roleKeys.getLong(ColumnName.ID);
    }

    private long findStatusId(PreparedStatement statusPreparedStatement, User.StatusType statusType) throws SQLException {
        statusPreparedStatement.setString(1, statusType.toString());
        ResultSet statusKeys = statusPreparedStatement.executeQuery();
        statusKeys.next();
        return statusKeys.getLong(ColumnName.ID);
    }
}
