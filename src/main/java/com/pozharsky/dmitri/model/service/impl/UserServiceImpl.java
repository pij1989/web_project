package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.command.RequestAttribute;
import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.UserDao;
import com.pozharsky.dmitri.model.dao.impl.UserDaoImpl;
import com.pozharsky.dmitri.model.entity.RoleType;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.util.PasswordEncryptor;
import com.pozharsky.dmitri.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    public static final UserServiceImpl INSTANCE = new UserServiceImpl();

    private UserServiceImpl() {
    }

    @Override
    public List<String> checkEmailAndPassword(String email, String password) throws ServiceException {
        UserValidator validator = UserValidator.INSTANCE;
        List<String> errors = new ArrayList<>();
        if (!validator.isEmail(email) || !validator.isPassword(password)) {
            errors.add(RequestAttribute.ERROR_EMAIL_OR_PASSWORD);
        }
        return errors;
    }

    @Override
    public boolean loginUser(String email, String password) throws ServiceException {
        try {
            UserDao userDao = UserDaoImpl.getInstance();
            String findPassword = userDao.findPasswordByEmail(email);
            if (!findPassword.isBlank()) {
                String hashPassword = PasswordEncryptor.encryptPassword(password);
                return findPassword.equals(hashPassword);
            } else {
                return false;
            }
        } catch (DaoException | NoSuchAlgorithmException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean registrationUser(String firstName, String lastName, String username, String email, String password) throws ServiceException {
        try {
            UserDao userDao = UserDaoImpl.getInstance();
            Optional<User> optionalUser = userDao.findUserByEmail(email);
            if (optionalUser.isEmpty()) {
                String hashPassword = PasswordEncryptor.encryptPassword(password);
                User user = new User(firstName, lastName, username, email, hashPassword, RoleType.USER, StatusType.ACTIVE);
                return userDao.create(user);
            } else {
                return false;
            }
        } catch (DaoException | NoSuchAlgorithmException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
