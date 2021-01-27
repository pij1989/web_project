package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.command.RequestAttribute;
import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.UserDao;
import com.pozharsky.dmitri.model.dao.impl.UserDaoImpl;
import com.pozharsky.dmitri.model.entity.RoleType;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.Token;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.util.PasswordEncrypter;
import com.pozharsky.dmitri.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final long EXPIRE_TIME = 15L;
    public static final UserServiceImpl INSTANCE = new UserServiceImpl();

    private UserServiceImpl() {
    }

    @Override
    public List<String> checkEmailAndPassword(String email, String password) {
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
            boolean isLogin = false;
            UserDao userDao = UserDaoImpl.getInstance();
            String findPassword = userDao.findPasswordByEmail(email);
            if (!findPassword.isBlank()) {
                String hashPassword = PasswordEncrypter.encryptPassword(password);
                isLogin = PasswordEncrypter.checkPassword(password, hashPassword);
            }
            return isLogin;
        } catch (DaoException e) {
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
                String hashPassword = PasswordEncrypter.encryptPassword(password);
                Token token = createVerificationToken();
                User user = new User(firstName, lastName, username, email, RoleType.USER, StatusType.WAIT_ACTIVE, token);
                return userDao.create(user, hashPassword);
            } else {
                return false;
            }
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    private Token createVerificationToken() {
        String tokenValue = UUID.randomUUID().toString();
        LocalDateTime timeCreate = LocalDateTime.now();
        LocalDateTime timeExpire = timeCreate.plusMinutes(EXPIRE_TIME);
        return new Token(tokenValue, timeCreate, timeExpire);
    }
}
