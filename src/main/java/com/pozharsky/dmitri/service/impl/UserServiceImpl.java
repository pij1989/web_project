package com.pozharsky.dmitri.service.impl;

import com.pozharsky.dmitri.command.RequestAttribute;
import com.pozharsky.dmitri.dao.UserDao;
import com.pozharsky.dmitri.dao.impl.UserDaoImpl;
import com.pozharsky.dmitri.entity.RoleType;
import com.pozharsky.dmitri.entity.User;
import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.service.UserService;
import com.pozharsky.dmitri.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public boolean checkUser(String email, String password) throws ServiceException {
        try {
            UserDao userDao = UserDaoImpl.INSTANCE;
            Optional<User> optionalUser = userDao.findUserByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                logger.debug("User: " + user);
                return user.getPassword().equals(password);
            } else {
                return false;
            }
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean createUser(String firstName, String lastName, String email, String password) throws ServiceException {
        try {
            UserDao userDao = UserDaoImpl.INSTANCE;
            Optional<User> optionalUser = userDao.findUserByEmail(email);
            if(optionalUser.isEmpty()){
                User user = new User(firstName, lastName, email, password, RoleType.USER);
                return userDao.create(user);
            } else {
                return false;
            }
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
